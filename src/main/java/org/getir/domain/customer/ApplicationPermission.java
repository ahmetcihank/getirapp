package org.getir.domain.customer;

import lombok.Getter;

public enum ApplicationPermission {

    READ("read"),
    WRITE("write");

    @Getter
    private final String permission;

    ApplicationPermission(String permission) {
        this.permission = permission;
    }

}
