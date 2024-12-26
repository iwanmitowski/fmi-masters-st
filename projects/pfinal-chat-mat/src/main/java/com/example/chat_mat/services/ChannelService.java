package com.example.chat_mat.services;

import com.example.chat_mat.dtos.ChannelDTO;
import com.example.chat_mat.entities.*;
import com.example.chat_mat.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final ChannelMembershipRepository channelMembershipRepository;
    private final RoleRepository roleRepository;

    public ChannelDTO createChannel(Integer userId, ChannelDTO channelRequest) {
        var user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found"));

        var channel = new Channel();
        channel.setName(channelRequest.getName());
        channel.setIsDeleted(false);
        channel = channelRepository.save(channel);

        var ownerRole = roleRepository.findByRoleName("OWNER").orElseThrow(() ->
                new IllegalArgumentException("Role 'OWNER' not found"));

        var membership = new ChannelMembership();
        membership.setChannel(channel);
        membership.setUser(user);
        membership.setRole(ownerRole);
        channelMembershipRepository.save(membership);

        return mapToChannelDTO(channel);
    }

    public void editChannelName(Integer ownerId, Integer channelId, String newName) {
        var channel = channelRepository.findById(channelId).orElseThrow(() ->
                new IllegalArgumentException("Channel not found"));

        var ownerRole = roleRepository.findByRoleName("OWNER").orElseThrow(() ->
                new IllegalArgumentException("Role 'OWNER' not found"));

        var isOwner = channelMembershipRepository.existsByChannelIdAndUserIdAndRoleId(
                channelId, ownerId, ownerRole.getId());
        if (!isOwner) {
            throw new IllegalArgumentException("User is not owner of the channel");
        }

        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Channel name cannot be empty");
        }

        channel.setName(newName.trim());
        channelRepository.save(channel);
    }

    public List<ChannelDTO> getUserChannels(Integer userId) {
         var user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found"));

        return channelMembershipRepository.findByUserId(userId)
                .stream()
                .filter(membership -> !membership.getChannel().getIsDeleted())
                .map(membership -> mapToChannelDTO(membership.getChannel(), membership.getRole().getId()))
                .toList();
    }

    public void deleteChannel(Integer userId, Integer channelId) {
        var channel = channelRepository.findById(channelId).orElseThrow(() ->
                new IllegalArgumentException("Channel not found"));

        var ownerRole = roleRepository.findByRoleName("OWNER").orElseThrow(() ->
                new IllegalArgumentException("Role 'OWNER' not found"));
        var ownerRoleId = ownerRole.getId();

        var isOwner = channelMembershipRepository.existsByChannelIdAndUserIdAndRoleId(
                channelId, userId, ownerRoleId);
        if (!isOwner) {
            throw new IllegalArgumentException("User is not the owner of the channel");
        }

        channel.setIsDeleted(true);
        channelRepository.save(channel);
    }

    public void addGuestToChannel(Integer userId, Integer channelId, Integer guestId) {
        var channel = channelRepository.findById(channelId).orElseThrow(() ->
                new IllegalArgumentException("Channel not found"));

        var ownerRole = roleRepository.findByRoleName("OWNER").orElseThrow(() ->
                new IllegalArgumentException("Role 'OWNER' not found"));
        var adminRole = roleRepository.findByRoleName("ADMIN").orElseThrow(() ->
                new IllegalArgumentException("Role 'ADMIN' not found"));

        var hasPermission =
                channelMembershipRepository.existsByChannelIdAndUserIdAndRoleId(channelId, userId, ownerRole.getId())
                || channelMembershipRepository.existsByChannelIdAndUserIdAndRoleId(channelId, userId, adminRole.getId());

        if (!hasPermission) {
            throw new IllegalArgumentException("Only the channel owner or admin can add guests");
        }

        var guest = userRepository.findById(guestId).orElseThrow(() ->
                new IllegalArgumentException("Guest user not found"));

        var isAlreadyMember = channelMembershipRepository.existsByChannelIdAndUserId(channelId, guestId);
        if (isAlreadyMember) {
            throw new IllegalArgumentException("User is already a member of the channel");
        }

        var guestRole = roleRepository.findByRoleName("GUEST").orElseThrow(() ->
                new IllegalArgumentException("Role 'GUEST' not found"));

        var newMembership = new ChannelMembership();
        newMembership.setChannel(channel);
        newMembership.setUser(guest);
        newMembership.setRole(guestRole);
        channelMembershipRepository.save(newMembership);
    }

    public void removeGuestFromChannel(Integer ownerId, Integer channelId, Integer guestId) {
        var channel = channelRepository.findById(channelId).orElseThrow(() ->
                new IllegalArgumentException("Channel not found"));

        var ownerRole = roleRepository.findByRoleName("OWNER").orElseThrow(() ->
                new IllegalArgumentException("Role 'OWNER' not found"));

        var isOwner = channelMembershipRepository.existsByChannelIdAndUserIdAndRoleId(
                channelId, ownerId, ownerRole.getId());
        if (!isOwner) {
            throw new IllegalArgumentException("Only the channel owner can remove users");
        }

        var guestRole = roleRepository.findByRoleName("GUEST").orElseThrow(() ->
                new IllegalArgumentException("Role 'GUEST' not found"));

        var guestMembership = channelMembershipRepository
                .findByChannelIdAndUserIdAndRoleId(channelId, guestId, guestRole.getId())
                .orElseThrow(() -> new IllegalArgumentException("User is not a guest in this channel"));

        channelMembershipRepository.delete(guestMembership);
    }

    public void promoteToAdmin(Integer ownerId, Integer channelId, Integer guestId) {
        var channel = channelRepository.findById(channelId).orElseThrow(() ->
                new IllegalArgumentException("Channel not found"));

        var ownerRole = roleRepository.findByRoleName("OWNER").orElseThrow(() ->
                new IllegalArgumentException("Role 'OWNER' not found"));

        boolean isOwner = channelMembershipRepository.existsByChannelIdAndUserIdAndRoleId(
                channelId, ownerId, ownerRole.getId());
        if (!isOwner) {
            throw new IllegalArgumentException("Only the channel owner can promote users");
        }

        var guestRole = roleRepository.findByRoleName("GUEST").orElseThrow(() ->
                new IllegalArgumentException("Role 'GUEST' not found"));

        var membership = channelMembershipRepository.findByChannelIdAndUserIdAndRoleId(channelId, guestId, guestRole.getId())
                .orElseThrow(() -> new IllegalArgumentException("User is not a GUEST in this channel"));

        var isPromotingOwner = channelMembershipRepository.existsByChannelIdAndUserIdAndRoleId(channelId, guestId, ownerRole.getId());
        if (isPromotingOwner) {
            throw new IllegalArgumentException("Cannot change the role of OWNER");
        }

        var adminRole = roleRepository.findByRoleName("ADMIN").orElseThrow(() ->
                new IllegalArgumentException("Role 'ADMIN' not found"));
        membership.setRole(adminRole);
        channelMembershipRepository.save(membership);
    }

    private ChannelDTO mapToChannelDTO(Channel channel) {
        ChannelDTO dto = new ChannelDTO();
        dto.setId(channel.getId());
        dto.setName(channel.getName());
        dto.setIsDeleted(channel.getIsDeleted());
        return dto;
    }

    private ChannelDTO mapToChannelDTO(Channel channel, Integer roleId) {
        ChannelDTO dto = new ChannelDTO();
        dto.setId(channel.getId());
        dto.setName(channel.getName());
        dto.setIsDeleted(channel.getIsDeleted());
        dto.setRoleId(roleId);
        return dto;
    }
}
