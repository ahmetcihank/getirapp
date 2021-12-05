package org.getir.dao;

import org.getir.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Customer findByUsername(String username);
    boolean existsByUsername(String username);

}
