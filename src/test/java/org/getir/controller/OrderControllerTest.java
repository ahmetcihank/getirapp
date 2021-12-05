package org.getir.controller;

import org.getir.domain.dto.*;
import org.getir.service.order.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Test
    void shouldCreateOrderResponse() {
        CartItemDto cartItemDto = CartItemDto
                .builder()
                .bookId(12)
                .count(12)
                .build();

        List<CartItemDto> cartItemDtos = new ArrayList<>(Arrays.asList(cartItemDto));

        ShoppingCartDto shoppingCartDto = ShoppingCartDto
                        .builder()
                        .cartItemDtos(cartItemDtos)
                        .build();

        String orderNumber = UUID.randomUUID().toString();
        String messageStr = String.format("ProductOrder: %s has been created", orderNumber);

        ObjectCreateResponseDto objectCreateResponseDto = ObjectCreateResponseDto.builder()
                                                                                .message(messageStr)
                                                                                .timestamp(new Date())
                                                                                .build();

        when(orderService.createOrder(shoppingCartDto)).thenReturn(objectCreateResponseDto);

        ResponseEntity<ObjectCreateResponseDto> responseObj = orderController.createOrder(shoppingCartDto);

        assertEquals(objectCreateResponseDto, responseObj.getBody());
        assertEquals(HttpStatus.CREATED, responseObj.getStatusCode());
    }

    @Test
    void shouldGetOrderById() {
        OrderItemResponseDto orderItemResponseDto = OrderItemResponseDto
                .builder()
                .bookName("test")
                .itemCount(2)
                .orderItemTotalPrice(new BigDecimal(22))
                .build();

        List<OrderItemResponseDto> orderItemResponseDtoList = new LinkedList<>();
        orderItemResponseDtoList.add(orderItemResponseDto);


        ProductOrderResponseDto productOrderResponseDto = ProductOrderResponseDto.builder()
                .createDate(new Date())
                .totalPrice(new BigDecimal(22))
                .orderNumber("123")
                .orderItemResponseDtos(orderItemResponseDtoList)
                .build();

        when(orderService.getOrderById(3l)).thenReturn(productOrderResponseDto);

        ResponseEntity<ProductOrderResponseDto> responseObj = orderController.getOrder(3l);

        assertEquals(productOrderResponseDto, responseObj.getBody());
        assertEquals(HttpStatus.OK, responseObj.getStatusCode());
    }

    @Test
    void getOrdersByDateIntervals() {
        OrderItemResponseDto orderItemResponseDto = OrderItemResponseDto
                .builder()
                .bookName("test")
                .itemCount(2)
                .orderItemTotalPrice(new BigDecimal(22))
                .build();

        List<OrderItemResponseDto> orderItemResponseDtoList = new LinkedList<>();
        orderItemResponseDtoList.add(orderItemResponseDto);

        ProductOrderResponseDto productOrderResponseDto = ProductOrderResponseDto.builder()
                .createDate(new Date())
                .totalPrice(new BigDecimal(22))
                .orderNumber("123")
                .orderItemResponseDtos(orderItemResponseDtoList)
                .build();
        List<ProductOrderResponseDto> productOrderResponseDtos = new LinkedList<>(Arrays.asList(productOrderResponseDto));

        when(orderService.getOrdersByTimeInterval(any(Date.class), any(Date.class))).thenReturn(productOrderResponseDtos);

        ResponseEntity<List<ProductOrderResponseDto>> responseObj = orderController.getOrdersByDateIntervals(new Date(), new Date());
        assertEquals(productOrderResponseDtos, responseObj.getBody());
        assertEquals(HttpStatus.OK, responseObj.getStatusCode());
    }

    @Test
    void shouldGetGetStatistics() {
        MonthlyStatisticDto monthlyStatisticDto = MonthlyStatisticDto
                .builder()
                .totalBookCount(4)
                .totalPurchasedAmount(new BigDecimal(34))
                .totalOrderCount(1)
                .build();

        List<MonthlyStatisticDto> monthlyStatisticDtos = new ArrayList<>(Arrays.asList(monthlyStatisticDto));

        when(orderService.getStatsByMonth(3l)).thenReturn(monthlyStatisticDtos);

        ResponseEntity<List<MonthlyStatisticDto>> responseObj = orderController.getStatistics(3l);
        assertEquals(monthlyStatisticDtos, responseObj.getBody());
        assertEquals(HttpStatus.OK, responseObj.getStatusCode());
    }
}