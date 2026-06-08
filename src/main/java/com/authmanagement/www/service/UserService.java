package com.authmanagement.www.service;

import com.authmanagement.www.dto.RegisterRequestDto;
import com.authmanagement.www.model.User;
import com.authmanagement.www.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void saveUser(RegisterRequestDto registerRequestDto) {
        User user = new User();
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(registerRequestDto.getPassword());
        user.setRole("USER");
        userRepository.save(user);
    }
}
