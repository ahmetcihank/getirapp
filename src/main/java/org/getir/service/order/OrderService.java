package org.getir.service.order;

import org.getir.domain.dto.MonthlyStatisticDto;
import org.getir.domain.dto.ObjectCreateResponseDto;
import org.getir.domain.dto.ProductOrderResponseDto;
import org.getir.domain.dto.ShoppingCartDto;

import java.util.Date;
import java.util.List;

public interface OrderService {

    public ObjectCreateResponseDto createOrder(ShoppingCartDto shoppingCartDto);
    public ProductOrderResponseDto getOrderById(long id);
    public List<ProductOrderResponseDto> getOrdersByTimeInterval(Date startDate, Date endDate);
    public List<MonthlyStatisticDto> getStatsByMonth(long userId);



}
