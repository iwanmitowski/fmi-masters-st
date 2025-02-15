package com.example.chat_mat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String email;
    private Boolean isDeleted;
    private List<UserDTO> friendshipsInitiatedUsers;
    private List<UserDTO> friendshipsReceivedUsers;
}
