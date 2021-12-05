package org.getir.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderItemResponseDto {

    private String bookName;
    private BigDecimal orderItemTotalPrice;
    private int itemCount;


}
