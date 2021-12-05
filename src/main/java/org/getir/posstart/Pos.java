package org.getir.posstart;

import org.getir.dao.CustomerRepository;
import org.getir.dao.RoleRepository;
import org.getir.domain.book.Book;
import org.getir.domain.customer.Customer;
import org.getir.domain.customer.Role;
import org.getir.domain.customer.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
public class Pos {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @PostConstruct
    public void appStart() {
        Role role = Role.builder().name(RoleType.SUPERUSER).build();
        Role role1 = Role.builder().name(RoleType.CUSTOMER).build();

        Set<Role> mRoles = new HashSet<>();
        mRoles.add(role1);
        mRoles.add(role);

        if(!customerRepository.existsByUsername("cihan")) {
            customerRepository.save(Customer.builder().name("cihan").username("cihan").password(passwordEncoder.encode("1234")).roles(mRoles).build());
        }
    }

}
