package com.example.chat_mat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipDTO {
    private Integer id;
    private UserDTO currentUser;
    private UserDTO friend;
}
