package com.example.chat_mat.repositories;

import com.example.chat_mat.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByChannelIdOrderByTimestampAsc(Integer channelId);
}
