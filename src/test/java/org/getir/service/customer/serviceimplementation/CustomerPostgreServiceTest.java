package org.getir.service.customer.serviceimplementation;

import org.getir.dao.CustomerRepository;
import org.getir.domain.book.Book;
import org.getir.domain.customer.Customer;
import org.getir.domain.dto.CustomerRequestDto;
import org.getir.domain.dto.ObjectCreateResponseDto;
import org.getir.domain.dto.ProductOrderResponseDto;
import org.getir.domain.order.OrderItem;
import org.getir.domain.order.OrderStatus;
import org.getir.domain.order.ProductOrder;
import org.getir.service.commons.UserService;
import org.getir.service.order.util.ProductOrderResponseConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerPostgreServiceTest {

    @InjectMocks
    private CustomerPostgreService customerPostgreService;

    @Mock
    private  CustomerRepository customerRepository;

    @Mock
    private  PasswordEncoder passwordEncoder;

    @Mock
    private  UserService userService;

    @Test
    void shouldCreateCustomer() {
        CustomerRequestDto customerRequestDto = CustomerRequestDto
                                                        .builder()
                                                        .name("test")
                                                        .password("12")
                                                        .username("test12")
                                                        .build();

        when(customerRepository.existsByUsername(customerRequestDto.getUsername())).thenReturn(false);

        ObjectCreateResponseDto objectCreateResponseDto =  customerPostgreService.createCustomer(customerRequestDto);

        assertTrue("username: test12 has been created".equals(objectCreateResponseDto.getMessage()) );
    }

    @Test
    void getUserOrders() {
        Book book = Book.builder().stock(23).name("testt").build();

        OrderItem orderItem = OrderItem
                .builder()
                .totalPrice(new BigDecimal(22))
                .book(book)
                .build();

        List<OrderItem> orderItems = new ArrayList<>(Arrays.asList(orderItem));

        ProductOrder productOrder = ProductOrder
                .builder()
                .orderNumber("12")
                .orderStatus(OrderStatus.SUCCESS)
                .price(new BigDecimal(22))
                .orderItem(orderItems)
                .build();

        Customer customer = Customer.builder()
                .name("dene")
                .username("test")
                .productOrder(new ArrayList<ProductOrder>(Arrays
                        .asList(productOrder)))
                .build();

        when(userService.getCurrentCustomer()).thenReturn(customer);

        ProductOrderResponseDto productOrderResponseDto = ProductOrderResponseConverter.convert(productOrder);

       List<ProductOrderResponseDto> productOrderResponseDtos = customerPostgreService.getUserOrders();
       assertEquals(productOrderResponseDtos, new ArrayList<ProductOrderResponseDto>(Arrays.asList(productOrderResponseDto)) );

    }
}