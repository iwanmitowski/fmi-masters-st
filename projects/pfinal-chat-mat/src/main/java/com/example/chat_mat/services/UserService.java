package com.example.chat_mat.services;

import com.example.chat_mat.entities.User;
import com.example.chat_mat.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setIsDeleted(false);

        return userRepository.save(user);
    }

    public List<User> searchUsers(String email) {
        if (email != null && !email.isEmpty()) {
            return userRepository.findByEmailContaining(email);
        } else {
            return userRepository.findAll();
        }
    }

}
