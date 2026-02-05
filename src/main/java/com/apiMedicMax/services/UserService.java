package com.apiMedicMax.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apiMedicMax.dto.UpdateProfileRequest;
import com.apiMedicMax.dto.UserProfileResponse;
import com.apiMedicMax.dto.UserResponse;
import com.apiMedicMax.repositories.UserRepository;
import com.apiMedicMax.models.User;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserResponse> findUsers(){
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public UserProfileResponse getProfile(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new UserProfileResponse(user.getUsername(), user.getEmail(), user.getAddress());
    }

    public UserProfileResponse updateOwnProfile(String email, UpdateProfileRequest request) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            user.setUsername(request.getUsername());
        }
        if (request.getAddress() != null && !request.getAddress().isBlank()) {
            user.setAddress(request.getAddress());
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updated = userRepository.save(user);
        return new UserProfileResponse(updated.getUsername(), updated.getEmail(), updated.getAddress());
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    private UserResponse toResponse(User user) {
        Set<String> roles = user.getRoles().stream().map(rol -> rol.getNombre()).collect(Collectors.toSet());
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getAddress(), roles);
    }
}
