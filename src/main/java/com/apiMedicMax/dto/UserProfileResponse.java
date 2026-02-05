package com.apiMedicMax.dto;

public class UserProfileResponse {
    private String username;
    private String email;
    private String address;

    public UserProfileResponse(String username, String email, String address){
        this.username = username;
        this.email = email;
        this.address = address;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public String getAddress() {
        return address;
    }
    
}
