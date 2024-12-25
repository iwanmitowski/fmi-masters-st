package com.example.chat_mat.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "friendships")
@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "current_user_id", nullable = false)
    private User currentUser;

    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private User friend;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;
}
