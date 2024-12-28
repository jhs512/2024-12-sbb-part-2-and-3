package com.ll.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole {
    Admin("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String role) {
        this.role = role;
    }

    private String role;
}
