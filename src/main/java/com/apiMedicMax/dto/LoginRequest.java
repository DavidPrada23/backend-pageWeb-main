package com.apiMedicMax.dto;

import lombok.*;

@Setter
@Getter

public class LoginRequest {
    private String email;
    private String password;

    // Se crea el constructor vacío
    public LoginRequest(){

    }

    public LoginRequest(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void getPassword(String password){
        this.password = password;
    }

}
