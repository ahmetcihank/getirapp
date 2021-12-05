package org.getir.service.order.serviceimplementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.getir.dao.BookRepository;
import org.getir.dao.OrderRepository;
import org.getir.domain.book.Book;
import org.getir.domain.customer.Customer;
import org.getir.domain.dto.*;
import org.getir.domain.order.ProductOrder;
import org.getir.domain.order.OrderItem;
import org.getir.domain.order.OrderStatus;
import org.getir.exception.BookNotFindException;
import org.getir.exception.ItemNotFoundException;
import org.getir.service.commons.UserService;
import org.getir.service.order.OrderService;
import org.getir.service.order.util.ProductOrderResponseConverter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderPostgreService implements OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final UserService userService;
    private final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July",
                                           "August", "September", "October", "November", "December"};

    @Override
    public ObjectCreateResponseDto createOrder(ShoppingCartDto shoppingCartDto) {
        List<OrderItem> orderItems = orderItemConverter(shoppingCartDto);
        BigDecimal orderPrice = claculatePrice(orderItems);
        Customer customer = userService.getCurrentCustomer();
        String orderNumber = UUID.randomUUID().toString();

        ProductOrder productOrder = ProductOrder.builder()
                        .customer(customer)
                        .orderItem(orderItems)
                        .orderStatus(OrderStatus.PENDING)
                        .orderNumber(orderNumber)
                        .price(orderPrice)
                        .build();

        orderItems.forEach(oi->oi.setProductOrder(productOrder));
        orderRepository.save(productOrder);
        updateStock(orderNumber);

        String messageStr = String.format("ProductOrder: %s has been created", orderNumber);

        return ObjectCreateResponseDto.builder()
                                      .message(messageStr)
                                      .timestamp(new Date())
                                      .build();
    }

    private void updateStock(String orderNumber) {
      ProductOrder productOrder = orderRepository.findByOrderNumber(orderNumber);

      for(OrderItem orderItem: productOrder.getOrderItem()) {
          Book book = orderItem.getBook();
          int stockCount = book.getStock();
          int remainingCount = stockCount - orderItem.getProductCount();

          if(remainingCount<0) {
              throw new ItemNotFoundException("Insufficent Stock");
          }

          BookRequestDto bookRequestDto = BookRequestDto.builder()
                                                        .author(book.getAuthor())
                                                        .name(book.getName())
                                                        .stockCount(remainingCount)
                                                        .build();

          bookRepository.updateBookStock(bookRequestDto);
      }

      productOrder.setOrderStatus(OrderStatus.SUCCESS);
      orderRepository.save(productOrder);
    }

    @Override
    public ProductOrderResponseDto getOrderById(long id) {
        Optional<ProductOrder> productOrder = orderRepository.findById(id);

        if(productOrder.isPresent()) {
            return ProductOrderResponseConverter.convert(productOrder.get());
        }

        throw new ItemNotFoundException("There is no order associated with this id");
    }

    @Override
    public List<ProductOrderResponseDto> getOrdersByTimeInterval(Date startDate, Date endDate) {
        List<ProductOrder> productOrders = orderRepository.getOrdersByTimeInterval(startDate, endDate);

        return productOrders.stream().map(ProductOrderResponseConverter::convert)
                                     .collect(Collectors.toList());
    }

    @Override
    public List<MonthlyStatisticDto> getStatsByMonth(long userId) {
        Calendar calendar = Calendar.getInstance();
        Integer month =  calendar.get(Calendar.MONTH)+1;
        List<MonthlyStatisticDto> monthlyStatisticDtos = new ArrayList<>();

        for(int i = month; i>0; i--) {
            List<ProductOrder> productOrder = orderRepository.getOrderByMonth(i,userId);
            MonthlyStatisticDto monthlyStatisticDto = extractStats(productOrder);
            monthlyStatisticDto.setMonth(monthNames[i-1]);
            monthlyStatisticDtos.add(monthlyStatisticDto);
        }
        return monthlyStatisticDtos;
    }

    private MonthlyStatisticDto extractStats(List<ProductOrder> productOrders) {
        int totalOrderCount = productOrders.size();
        int totalBookCount = 0;
        BigDecimal totalAmount = new BigDecimal(0);

        for (ProductOrder productOrder : productOrders) {
            totalBookCount += productOrder.getOrderItem().stream()
                                                         .mapToInt(oi->oi.getProductCount())
                                                         .sum();

            totalAmount = totalAmount.add(productOrder.getPrice());
        }

        return MonthlyStatisticDto.builder()
                                  .totalBookCount(totalBookCount)
                                  .totalOrderCount(totalOrderCount)
                                  .totalPurchasedAmount(totalAmount)
                                  .build();
    }

    //TODO vakit kalırsa stream e çevir.
    public BigDecimal claculatePrice(List<OrderItem> orderItems) {
        BigDecimal price = new BigDecimal(0);
        for(OrderItem orderItem: orderItems) {
           price = price.add(orderItem.getTotalPrice());
        }
        return price;
    }

    private  List<OrderItem> orderItemConverter(ShoppingCartDto shoppingCartDto) {
        List<OrderItem> orderItems = new LinkedList<>();

        for(CartItemDto cartItemDto: shoppingCartDto.getCartItemDtos()) {
            Optional<Book> bookdb = bookRepository.findById(cartItemDto.getBookId());
            if (bookdb.isPresent()) {
                Book book = bookdb.get();

                if(book.getStock() == 0) {
                    throw new ItemNotFoundException("Insufficent stock");
                }

                BigDecimal itemPrice = book.getPrice().multiply(BigDecimal.valueOf(cartItemDto.getCount()));

                OrderItem orderItem = OrderItem.builder()
                                               .productCount(cartItemDto.getCount())
                                               .book(book)
                                               .totalPrice(itemPrice)
                                               .totalPrice(itemPrice)
                                               .build();
                orderItems.add(orderItem);
            } else {
                throw new BookNotFindException("Book does not exist in database please enter valid id");
            }
        }
        return orderItems;
    }
}
