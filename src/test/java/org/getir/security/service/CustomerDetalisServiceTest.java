package org.getir.security.service;

import org.getir.dao.CustomerRepository;
import org.getir.domain.customer.Customer;
import org.getir.domain.customer.CustomerDetail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerDetalisServiceTest {

    @InjectMocks
    private CustomerDetalisService customerDetalisService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void shouldLoadUserByUsername() {
        Customer customerExpected = Customer
                        .builder()
                        .username("jack")
                        .password("test")
                        .name("test")
                        .build();

        UserDetails userDetailsExpected = new CustomerDetail(customerExpected);
        when(customerRepository.findByUsername("jack")).thenReturn(customerExpected);

        UserDetails userDetails = customerDetalisService.loadUserByUsername("jack");

        assertEquals(userDetailsExpected.getUsername(), userDetails.getUsername());
        assertEquals(userDetailsExpected.getPassword(), userDetails.getPassword());
    }
}