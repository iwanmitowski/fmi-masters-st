package com.example.chat_mat.dtos;

import com.example.chat_mat.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelMembershipDTO {
    private ChannelDTO channel;
    private UserDTO user;
    private RoleDTO role;
}
