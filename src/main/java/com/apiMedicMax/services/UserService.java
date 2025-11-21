package com.apiMedicMax.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiMedicMax.repositories.UserRepository;
import com.apiMedicMax.models.User;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Correo ya registrado");
        }
        return userRepository.save(user);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
