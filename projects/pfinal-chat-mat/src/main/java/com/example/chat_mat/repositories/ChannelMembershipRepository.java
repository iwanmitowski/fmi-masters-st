package com.example.chat_mat.repositories;

import com.example.chat_mat.entities.ChannelMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelMembershipRepository extends JpaRepository<ChannelMembership, Integer> {
    boolean existsByChannelIdAndUserIdAndRoleId(Integer channelId, Integer userId, Integer roleId);
    List<ChannelMembership> findByUserId(Integer userId);
    Optional<ChannelMembership> findByChannelIdAndUserIdAndRoleId(Integer channelId, Integer userId, Integer roleId);
    boolean existsByChannelIdAndUserId(Integer channelId, Integer userId);
    List<ChannelMembership> findByChannelId(Integer channelId);
}
