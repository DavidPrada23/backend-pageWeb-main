package com.apiMedicMax.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

import com.apiMedicMax.dto.UserProfileResponse;
import com.apiMedicMax.dto.LoginRequest;
import com.apiMedicMax.dto.RegisterRequest;
import com.apiMedicMax.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login") //Inicia sesión y devuelve el token JWT.
    public ResponseEntity<?> login(@RequestBody LoginRequest loginrequest) {
        String token = authService.authenticateUser(loginrequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register") //Registra un nuevo usuario.
    public ResponseEntity<?> register(@RequestBody RegisterRequest user) {
        UserProfileResponse newUser = authService.registerUser(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/profile") //Devuelve la información del usuario autenticado.
    public ResponseEntity<?> getProfile(Authentication authentication) {
        UserProfileResponse profile = authService.getProfileByEmail(authentication.getName());
        return ResponseEntity.ok(profile);
    }
    
    
}
