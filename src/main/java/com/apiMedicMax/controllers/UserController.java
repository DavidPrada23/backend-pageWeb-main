package com.apiMedicMax.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import com.apiMedicMax.dto.UpdateProfileRequest;
import com.apiMedicMax.dto.UserProfileResponse;
import com.apiMedicMax.dto.UserResponse;
import com.apiMedicMax.services.UserService;
import com.apiMedicMax.models.User;

import java.util.List;

@RestController
@RequestMapping("/service")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.findUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getAddress(),
            user.getRoles().stream().map(rol -> rol.getNombre()).collect(java.util.stream.Collectors.toSet())
        ));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(Authentication authentication){
        return ResponseEntity.ok(userService.getProfile(authentication.getName()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateMyProfile(
            Authentication authentication,
            @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateOwnProfile(authentication.getName(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> delete(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        userService.delete(id);
        return ResponseEntity.ok(new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getAddress(),
            user.getRoles().stream().map(rol -> rol.getNombre()).collect(java.util.stream.Collectors.toSet())
        ));
    }
}
