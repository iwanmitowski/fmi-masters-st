package com.example.chat_mat.entities;

import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
//
//@Embeddable
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class ChannelMembershipId implements Serializable {
    @Column(name = "channel_id")
    private Integer channelId;

    @Column(name = "user_id")
    private Integer userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChannelMembershipId that = (ChannelMembershipId) o;
        return Objects.equals(channelId, that.channelId) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelId, userId);
    }
}