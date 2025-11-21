package com.apiMedicMax.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.apiMedicMax.dto.UserProfileResponse;
import com.apiMedicMax.dto.LoginRequest;
import com.apiMedicMax.services.AuthService;
import com.apiMedicMax.models.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


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
    public ResponseEntity<?> register(@RequestBody User user) {
        User newUser = authService.registerUser(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/profile") //Devuelve la información del usuario autenticado.
    public ResponseEntity<?> getProfile() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            UserDetails userDetails = (UserDetails) principal;
            //Crear un DTO con la información que deseas devolver.
            UserProfileResponse profile = new UserProfileResponse(userDetails.getUsername(), "email@ejemplo.com");
            return ResponseEntity.ok(profile);
        }
        return ResponseEntity.status(401).body("Usuario no autenticado");
    }
    
    
}
