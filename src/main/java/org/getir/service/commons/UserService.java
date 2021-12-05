package org.getir.service.commons;

import lombok.RequiredArgsConstructor;
import org.getir.dao.CustomerRepository;
import org.getir.domain.customer.Customer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final CustomerRepository customerRepository;

    public  Customer getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return customerRepository.findByUsername(username);
    }

}
