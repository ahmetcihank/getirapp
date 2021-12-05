package org.getir.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductOrderResponseDto {

    private String orderNumber;
    private Date createDate;
    private BigDecimal totalPrice;
    private List<OrderItemResponseDto> orderItemResponseDtos;

}
