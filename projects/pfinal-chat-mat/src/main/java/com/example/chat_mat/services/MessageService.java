package com.example.chat_mat.services;

import com.example.chat_mat.dtos.MessageDTO;
import com.example.chat_mat.entities.Channel;
import com.example.chat_mat.entities.Message;
import com.example.chat_mat.entities.User;
import com.example.chat_mat.repositories.ChannelRepository;
import com.example.chat_mat.repositories.MessageRepository;
import com.example.chat_mat.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    public MessageDTO sendMessage(MessageDTO messageDTO) {
        var channel = channelRepository.findById(messageDTO.getChannelId()).orElseThrow(() ->
                new IllegalArgumentException("Channel not found"));
        var user = userRepository.findById(messageDTO.getUserId()).orElseThrow(() ->
                new IllegalArgumentException("User not found"));

        var message = new Message();
        message.setContent(messageDTO.getContent());
        message.setTimestamp(LocalDateTime.now());
        message.setChannel(channel);
        message.setUser(user);
        message = messageRepository.save(message);

        return mapToMessageDTO(message);
    }

    public List<MessageDTO> getMessagesByChannel(Integer channelId) {
        return messageRepository.findByChannelIdOrderByTimestampDesc(channelId)
                .stream()
                .map(this::mapToMessageDTO)
                .collect(Collectors.toList());
    }

    private MessageDTO mapToMessageDTO(Message message) {
        return new MessageDTO(
                message.getId(),
                message.getContent(),
                message.getTimestamp(),
                message.getChannel().getId(),
                message.getUser().getId()
        );
    }
}
