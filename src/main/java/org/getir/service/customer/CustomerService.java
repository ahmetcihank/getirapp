package org.getir.service.customer;

import org.getir.domain.dto.CustomerRequestDto;
import org.getir.domain.dto.ObjectCreateResponseDto;
import org.getir.domain.dto.ProductOrderResponseDto;

import java.util.List;

public interface CustomerService {

    public ObjectCreateResponseDto createCustomer(CustomerRequestDto customerRequestDto);
    public List<ProductOrderResponseDto> getUserOrders();

}
