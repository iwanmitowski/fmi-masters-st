package com.example.chat_mat.repositories;

import com.example.chat_mat.entities.ChannelMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelMembershipRepository extends JpaRepository<ChannelMembership, Integer> {
}
