package com.example.chat_mat.controllers;

import com.example.chat_mat.dtos.ChannelDTO;
import com.example.chat_mat.http.AppResponse;
import com.example.chat_mat.services.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @PostMapping("/channels")
    public ResponseEntity<?> createChannel(@RequestParam Integer userId, @RequestBody ChannelDTO channelRequest) {
        try {
            var result = channelService.createChannel(userId, channelRequest);
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

    @PutMapping("/channels")
    public ResponseEntity<?> editChannelName(
            @RequestParam Integer ownerId,
            @RequestBody ChannelDTO channel) {
        try {
            channelService.editChannelName(ownerId, channel.getId(), channel.getName());
            return AppResponse.success()
                    .withMessage("Channel name updated successfully")
                    .withCode(HttpStatus.OK)
                    .build();
        } catch (IllegalArgumentException ex) {
            return AppResponse.error()
                    .withMessage(ex.getMessage())
                    .withCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @GetMapping("/users/{userId}/channels")
    public ResponseEntity<?> getUserChannels(@PathVariable Integer userId) {
        try {
            var channels = channelService.getUserChannels(userId);
            return AppResponse.success()
                    .withMessage("Channels fetched successfully")
                    .withData(channels)
                    .withCode(HttpStatus.OK)
                    .build();
        } catch (IllegalArgumentException ex) {
            return AppResponse.error()
                    .withMessage(ex.getMessage())
                    .withCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @GetMapping("/channels/{channelId}/members")
    public ResponseEntity<?> getChannelMembers(@PathVariable Integer channelId) {
        try {
            var members = channelService.getChannelMembers(channelId);
            return AppResponse.success()
                    .withMessage("Channels fetched successfully")
                    .withData(members)
                    .withCode(HttpStatus.OK)
                    .build();
        } catch (IllegalArgumentException ex) {
            return AppResponse.error()
                    .withMessage("Channel not found")
                    .withCode(HttpStatus.OK)
                    .build();
        } catch (Exception ex) {
            return AppResponse.error()
                    .withMessage(ex.getMessage())
                    .withCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @DeleteMapping("/channels/{channelId}")
    public ResponseEntity<?> deleteChannel(@PathVariable Integer channelId, @RequestParam Integer userId) {
        try {
            channelService.deleteChannel(userId, channelId);
            return AppResponse.success()
                    .withMessage("Channel deleted successfully")
                    .withCode(HttpStatus.OK)
                    .build();
        } catch (IllegalArgumentException ex) {
            return AppResponse.error()
                    .withMessage(ex.getMessage())
                    .withCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @PostMapping("/channels/{channelId}/add-guest")
    public ResponseEntity<?> addGuestToChannel(
            @PathVariable Integer channelId,
            @RequestParam Integer userId,
            @RequestParam Integer guestId) {
        try {
            channelService.addGuestToChannel(userId, channelId, guestId);
            return AppResponse.success()
                    .withMessage("Guest added successfully")
                    .withCode(HttpStatus.CREATED)
                    .build();
        } catch (IllegalArgumentException ex) {
            return AppResponse.error()
                    .withMessage(ex.getMessage())
                    .withCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @DeleteMapping("/channels/{channelId}/remove-guest")
    public ResponseEntity<?> removeGuestFromChannel(
            @PathVariable Integer channelId,
            @RequestParam Integer ownerId,
            @RequestParam Integer guestId) {
        try {
            channelService.removeGuestFromChannel(ownerId, channelId, guestId);
            return AppResponse.success()
                    .withMessage("Guest removed successfully")
                    .withCode(HttpStatus.OK)
                    .build();
        } catch (IllegalArgumentException ex) {
            return AppResponse.error()
                    .withMessage(ex.getMessage())
                    .withCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @PostMapping("/channels/{channelId}/promote-to-admin")
    public ResponseEntity<?> promoteToAdmin(
            @PathVariable Integer channelId,
            @RequestParam Integer ownerId,
            @RequestParam Integer guestId) {
        try {
            channelService.promoteToAdmin(ownerId, channelId, guestId);
            return AppResponse.success()
                    .withMessage("User promoted to ADMIN successfully")
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
