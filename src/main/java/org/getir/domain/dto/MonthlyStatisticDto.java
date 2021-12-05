package org.getir.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyStatisticDto {
    private String month;
    private int totalOrderCount;
    private int totalBookCount;
    private BigDecimal totalPurchasedAmount;
}
