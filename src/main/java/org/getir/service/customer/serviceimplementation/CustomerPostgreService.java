package org.getir.service.customer.serviceimplementation;

import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.getir.dao.CustomerRepository;
import org.getir.domain.customer.Customer;
import org.getir.domain.customer.Role;
import org.getir.domain.dto.CustomerRequestDto;
import org.getir.domain.dto.ObjectCreateResponseDto;
import org.getir.domain.dto.ProductOrderResponseDto;
import org.getir.exception.AlreadyRegisteredException;
import org.getir.service.commons.UserService;
import org.getir.service.customer.CustomerService;
import org.getir.service.order.util.ProductOrderResponseConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.getir.domain.customer.RoleType.CUSTOMER;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerPostgreService implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public ObjectCreateResponseDto createCustomer(CustomerRequestDto customerRequestDto) {
        Role role = Role.builder().name(CUSTOMER).build();

        Customer customer = Customer.builder()
                                    .name(customerRequestDto.getName())
                                    .password(encodePassword(customerRequestDto
                                                                .getPassword()))
                                    .username(customerRequestDto.getUsername())
                                    .roles(Sets.newHashSet(role))
                                    .build();


        boolean existsCustomer = customerRepository.existsByUsername(customerRequestDto.getUsername());

        if(existsCustomer) {
            throw new AlreadyRegisteredException("This username is taken by another user");
        }

        customerRepository.save(customer);
        log.info("user has been created:{}", customerRequestDto.getUsername());

        String messageStr = String.format("username: %s has been created", customerRequestDto.getUsername());

        return ObjectCreateResponseDto
                            .builder()
                            .message(messageStr)
                            .timestamp(new Date())
                            .build();
    }


    public List<ProductOrderResponseDto> getUserOrders() {
       Customer customer = userService.getCurrentCustomer();

       return customer.getProductOrder().stream().map(ProductOrderResponseConverter::convert)
                                                      .collect(Collectors.toList());
    }


    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
