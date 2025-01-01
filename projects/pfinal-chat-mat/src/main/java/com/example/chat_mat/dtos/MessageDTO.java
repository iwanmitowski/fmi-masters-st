package com.example.chat_mat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Integer id;
    private String content;
    private LocalDateTime timestamp;
    private Integer channelId;
    private Integer userId;
}
