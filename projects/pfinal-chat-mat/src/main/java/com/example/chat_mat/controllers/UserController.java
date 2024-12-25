package com.example.chat_mat.controllers;

import com.example.chat_mat.entities.User;
import com.example.chat_mat.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        try {
            var result = userService.registerUser(user);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam(name = "search", required = false) String search) {
        var users = userService.searchUsers(search);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
