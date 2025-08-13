package com.kce.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private String uniqueId;

    public CustomUserDetails(String email, String password, String role, String uniqueId) {
        super(email, password, Collections.singletonList(new SimpleGrantedAuthority(role)));
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
    }
}
