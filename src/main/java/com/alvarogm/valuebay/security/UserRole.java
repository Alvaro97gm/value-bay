package com.alvarogm.valuebay.security;

import java.util.stream.Stream;

public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");

    UserRole(String name){}

    public static String[] roles(){
        return Stream.of(values()).map(UserRole::toString).toArray(String[]::new);
    }

    public static boolean contains(String role){
        for (UserRole ur : UserRole.values()) {
            if (ur.name().equals(role))
                return true;
        }
        return false;
    }
}
