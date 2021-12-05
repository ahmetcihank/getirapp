package org.getir.controller;

import org.getir.domain.dto.CustomerRequestDto;
import org.getir.domain.dto.ObjectCreateResponseDto;
import org.getir.domain.dto.ProductOrderResponseDto;
import org.getir.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(@Qualifier("customerPostgreService")CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<ObjectCreateResponseDto> createUser(@RequestBody @Valid CustomerRequestDto customerRequestDto) {
       ObjectCreateResponseDto objectCreateResponseDto = customerService.createCustomer(customerRequestDto);

       return ResponseEntity.status(HttpStatus.CREATED).body(objectCreateResponseDto);
    }

    @GetMapping("/user-orders") //TODO pagination
    public ResponseEntity<List<ProductOrderResponseDto>> getUserOrders() {
        List<ProductOrderResponseDto> productOrderResponseDto = customerService.getUserOrders();

        return ResponseEntity.status(HttpStatus.OK).body(productOrderResponseDto);
    }

}

