package com.stylelab.user.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserRole {
    ROLE_USER("USER"),
    ROLE_STORE_OWNER("STORE_OWNER"),
    ROLE_STORE_STAFF("STORE_STAFF");

    private final String role;
    
    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(usersRole -> usersRole.name().equalsIgnoreCase(role))
                .findAny()
                .orElse(null);
    }
}
