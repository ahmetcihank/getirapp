package org.getir.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDto {

    @NotBlank(message = "name cannot be empty")
    private String name;
    @NotBlank(message = "author cannot be empty")
    private String author;
    @Min(value = 1, message = "stockCount should be greater then zero ")
    private int stockCount;
    @Min(value = 1, message = "stockCount should be greater then zero ")
    private BigDecimal price;

}
