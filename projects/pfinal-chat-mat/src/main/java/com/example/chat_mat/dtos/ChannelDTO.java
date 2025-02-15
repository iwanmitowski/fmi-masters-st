package com.example.chat_mat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDTO {
    private Integer id;
    private String name;
    private Boolean isDeleted;
    private Integer roleId;
}
