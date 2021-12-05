package org.getir.controller;

import org.getir.domain.dto.MonthlyStatisticDto;
import org.getir.domain.dto.ObjectCreateResponseDto;
import org.getir.domain.dto.ProductOrderResponseDto;
import org.getir.domain.dto.ShoppingCartDto;
import org.getir.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("productOrder")
@Validated
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(@Qualifier("orderPostgreService") OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-productOrder")
    public ResponseEntity<ObjectCreateResponseDto> createOrder(@RequestBody @Valid ShoppingCartDto shoppingCartDto) {
        ObjectCreateResponseDto objectCreateResponseDto = orderService.createOrder(shoppingCartDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(objectCreateResponseDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERUSER')")
    public ResponseEntity<ProductOrderResponseDto> getOrder(@PathVariable("id")Long productOrderId) {
        ProductOrderResponseDto productOrderResponseDto = orderService.getOrderById(productOrderId);

        return ResponseEntity.status(HttpStatus.OK).body(productOrderResponseDto);
    }

    @GetMapping(value="/date")
    public ResponseEntity<List<ProductOrderResponseDto>> getOrdersByDateIntervals(@RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") Date startDate,
                                                                                    @RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") Date endDate) {
        List<ProductOrderResponseDto> responseDto = orderService.getOrdersByTimeInterval(startDate, endDate);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERUSER')")
    @GetMapping(value = "/statistics/{userId}")
    public ResponseEntity<List<MonthlyStatisticDto>> getStatistics(@PathVariable("userId")long userId) {
        List<MonthlyStatisticDto> monthlyStats = orderService.getStatsByMonth( userId);

        return  ResponseEntity.status(HttpStatus.OK).body(monthlyStats);
    }

}
