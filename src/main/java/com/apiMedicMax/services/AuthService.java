package com.apiMedicMax.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.apiMedicMax.dto.LoginRequest;
import com.apiMedicMax.dto.RegisterRequest;
import com.apiMedicMax.dto.UserProfileResponse;
import com.apiMedicMax.exceptions.EmailAlreadyExistsException;
import com.apiMedicMax.models.Rol;
import com.apiMedicMax.repositories.UserRepository;
import com.apiMedicMax.repositories.RolRepository;
import com.apiMedicMax.models.User;
import com.apiMedicMax.security.JwtTokenProvider;

@Service
public class AuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String authenticateUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );
        //Genera el token JWT
        String jwt = jwtTokenProvider.generateToken(authentication);
        return jwt;
    }

    public UserProfileResponse registerUser(RegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("El correo ya está registrado");
        }

        Rol userRole = rolRepository.findByNombre("ROLE_USER")
            .orElseThrow(() -> new IllegalStateException("No existe el rol ROLE_USER en la base de datos"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(userRole));

        User saved = userRepository.save(user);
        return new UserProfileResponse(saved.getUsername(), saved.getEmail(), saved.getAddress());
    }

    public UserProfileResponse getProfileByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        return new UserProfileResponse(user.getUsername(), user.getEmail(), user.getAddress());
    }

}
