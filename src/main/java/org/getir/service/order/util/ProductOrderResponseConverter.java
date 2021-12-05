package org.getir.service.order.util;

import org.getir.domain.dto.OrderItemResponseDto;
import org.getir.domain.dto.ProductOrderResponseDto;
import org.getir.domain.order.ProductOrder;

import java.util.List;
import java.util.stream.Collectors;

public class ProductOrderResponseConverter {

    public static ProductOrderResponseDto convert(ProductOrder order) {
        List<OrderItemResponseDto> cartItemDtos = order.getOrderItem().stream().map(item ->new OrderItemResponseDto(item.getBook().getName(),
                                                                                                     item.getTotalPrice(), item.getProductCount()))
                                                                                                        .collect(Collectors.toList());

        return ProductOrderResponseDto.builder()
                        .orderNumber(order.getOrderNumber())
                        .createDate(order.getCreated())
                        .totalPrice(order.getPrice())
                        .orderItemResponseDtos(cartItemDtos)
                        .build();
    }

}
