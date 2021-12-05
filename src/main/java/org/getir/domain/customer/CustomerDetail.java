package org.getir.domain.customer;

import org.getir.domain.customer.ApplicationRole;
import org.getir.domain.customer.Customer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomerDetail implements UserDetails {

    private Customer customer;


    public CustomerDetail(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Collection<? extends SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        for(Role role: customer.getRoles()) {
            for(SimpleGrantedAuthority simpleGrantedAuthority: role.getName().getAuthorities()) {
                simpleGrantedAuthorities.add(simpleGrantedAuthority);
            }
        }
        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.customer.getPassword();
    }

    @Override
    public String getUsername() {
        return this.customer.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
