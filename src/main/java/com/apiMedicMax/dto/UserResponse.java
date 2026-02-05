package com.apiMedicMax.dto;

import java.util.Set;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String address;
    private Set<String> roles;

    public UserResponse(Long id, String username, String email, String address, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.address = address;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
