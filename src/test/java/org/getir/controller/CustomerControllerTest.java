package org.getir.controller;

import org.getir.domain.dto.CustomerRequestDto;
import org.getir.domain.dto.ObjectCreateResponseDto;
import org.getir.domain.dto.OrderItemResponseDto;
import org.getir.domain.dto.ProductOrderResponseDto;
import org.getir.service.customer.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Test
    void shouldReturnSuccessfullUserCreatedResponseWhenCorrectObjectGiven() {
        CustomerRequestDto customerRequestDto = CustomerRequestDto
                .builder()
                .name("test")
                .password("12")
                .username("test12")
                .build();

        String messageStr = String.format("username: %s has been created", customerRequestDto.getUsername());

        ObjectCreateResponseDto objectCreateResponseDto = ObjectCreateResponseDto
                .builder()
                .timestamp(new Date())
                .message(messageStr)
                .build();

        when(customerService.createCustomer(customerRequestDto)).thenReturn(objectCreateResponseDto);

        customerController.createUser(customerRequestDto);
        ResponseEntity<ObjectCreateResponseDto> responseObj = customerController.createUser(customerRequestDto);

        assertEquals(objectCreateResponseDto, responseObj.getBody());
        assertEquals(HttpStatus.CREATED, responseObj.getStatusCode());
    }

    @Test
    void shouldGetUserOrders() {
      OrderItemResponseDto orderItemResponseDto = OrderItemResponseDto
                        .builder()
                        .bookName("test")
                        .itemCount(2)
                        .orderItemTotalPrice(new BigDecimal(22))
                        .build();

      List<OrderItemResponseDto> orderItemResponseDtoList = new LinkedList<>();
      orderItemResponseDtoList.add(orderItemResponseDto);

      List<ProductOrderResponseDto> productOrderResponseDtos = new LinkedList<>();

      ProductOrderResponseDto productOrderResponseDto = ProductOrderResponseDto.builder()
              .createDate(new Date())
              .totalPrice(new BigDecimal(22))
              .orderNumber("123")
              .orderItemResponseDtos(orderItemResponseDtoList)
              .build();

      productOrderResponseDtos.add(productOrderResponseDto);

      when(customerService.getUserOrders()).thenReturn(productOrderResponseDtos);

      ResponseEntity<List<ProductOrderResponseDto>> responseObj = customerController.getUserOrders();

      assertEquals(productOrderResponseDtos, responseObj.getBody());
      assertEquals(HttpStatus.OK, responseObj.getStatusCode());
    }
}