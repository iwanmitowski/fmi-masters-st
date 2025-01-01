package com.example.chat_mat.controllers;

import com.example.chat_mat.entities.User;
import com.example.chat_mat.http.AppResponse;
import com.example.chat_mat.services.FriendshipService;
import com.example.chat_mat.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final FriendshipService friendshipService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            var result = userService.registerUser(user);
            return AppResponse.success()
                    .withData(result)
                    .withCode(HttpStatus.CREATED)
                    .build();
        } catch (IllegalArgumentException ex) {
            return AppResponse.error()
                    .withMessage(ex.getMessage())
                    .withCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> getUser(@RequestBody User credentials) {
        try {
            var user = userService.getUserByEmailAndPassword(credentials.getEmail(), credentials.getPassword());
            return AppResponse.success()
                    .withData(user)
                    .withCode(HttpStatus.OK)
                    .build();
        } catch (IllegalArgumentException ex) {
            return AppResponse.error()
                    .withMessage(ex.getMessage())
                    .withCode(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam(name = "search", required = false) String search) {
        var result = userService.searchUsers(search);
        return AppResponse.success()
                .withData(result)
                .withCode(HttpStatus.OK)
                .build();
    }

    @PostMapping("/add-friend")
    public ResponseEntity<?> addFriend(@RequestParam Integer currentUserId, @RequestParam Integer friendId) {
        try {
            var result = friendshipService.addFriend(currentUserId, friendId);
            return AppResponse.success()
                    .withData(result)
                    .withCode(HttpStatus.CREATED)
                    .build();
        } catch (Exception ex) {
            return AppResponse.error()
                    .withMessage(ex.getMessage())
                    .withCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
}
