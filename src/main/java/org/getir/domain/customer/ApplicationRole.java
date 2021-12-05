package org.getir.domain.customer;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.getir.domain.customer.ApplicationPermission;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static org.getir.domain.customer.ApplicationPermission.READ;
import static org.getir.domain.customer.ApplicationPermission.WRITE;

public enum ApplicationRole {
    CUSTOMER(Sets.newHashSet(READ)),
    SUPERUSER(Sets.newHashSet(WRITE, READ));


    @Getter
    private final Set<ApplicationPermission> permissions;

    ApplicationRole(Set<ApplicationPermission> permissions) {
        this.permissions = permissions;
    }


    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> permissions =  getPermissions().stream()
                .map(permission->new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return permissions;
    }

}
