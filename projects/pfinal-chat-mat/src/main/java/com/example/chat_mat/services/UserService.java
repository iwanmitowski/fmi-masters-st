package com.example.chat_mat.services;

import com.example.chat_mat.dtos.UserDTO;
import com.example.chat_mat.entities.User;
import com.example.chat_mat.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDTO registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setIsDeleted(false);

        return mapToUserDTO(userRepository.save(user));
    }

    public UserDTO getUserByEmailAndPassword(String email, String password) {
        var user = userRepository.findUserByEmailAndPassword(email, password)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return mapToUserDTO(user);
    }

    public List<UserDTO> searchUsers(String email) {
        if (email != null && !email.isEmpty()) {
            return userRepository.findByEmailContaining(email)
                    .stream()
                    .map(this::mapToUserDTO)
                    .collect(Collectors.toList());
        } else {
            return userRepository.findAll()
                    .stream()
                    .map(this::mapToUserDTO)
                    .collect(Collectors.toList());
        }
    }

    public UserDTO mapToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getIsDeleted(),
                user.getFriendshipsInitiated()
                        .stream()
                        .map(friendship -> mapFriendToDTO(friendship.getFriend()))
                        .collect(Collectors.toList()),
                user.getFriendshipsReceived()
                        .stream()
                        .map(friendship -> mapFriendToDTO(friendship.getCurrentUser()))
                        .collect(Collectors.toList())
        );
    }

    private UserDTO mapFriendToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getIsDeleted(),
                null,
                null
        );
    }
}
