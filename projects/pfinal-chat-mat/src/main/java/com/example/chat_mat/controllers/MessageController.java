package com.example.chat_mat.controllers;

import com.example.chat_mat.dtos.MessageDTO;
import com.example.chat_mat.http.AppResponse;
import com.example.chat_mat.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/messages")
    public ResponseEntity<?> sendMessage(
            @RequestBody MessageDTO messageDTO) {
        try {
            var message = messageService.sendMessage(messageDTO);
            return AppResponse.success()
                    .withMessage("Message sent successfully")
                    .withData(message)
                    .withCode(HttpStatus.CREATED)
                    .build();
        } catch (IllegalArgumentException ex) {
            return AppResponse.error()
                    .withMessage(ex.getMessage())
                    .withCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @GetMapping("/channels/{channelId}/messages")
    public ResponseEntity<?> getMessagesByChannel(@PathVariable Integer channelId) {
        try {
            var messages = messageService.getMessagesByChannel(channelId);
            return AppResponse.success()
                    .withData(messages)
                    .withCode(HttpStatus.OK)
                    .build();
        } catch (IllegalArgumentException ex) {
            return AppResponse.error()
                    .withMessage(ex.getMessage())
                    .withCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
}
