package org.getir.security.service;

import org.getir.dao.CustomerRepository;
import org.getir.domain.customer.Customer;
import org.getir.domain.customer.CustomerDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetalisService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerDetalisService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username);
        return new CustomerDetail(customer);
    }
}
