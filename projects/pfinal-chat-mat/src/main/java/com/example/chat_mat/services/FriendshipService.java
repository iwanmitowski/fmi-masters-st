package com.example.chat_mat.services;

import com.example.chat_mat.dtos.ChannelDTO;
import com.example.chat_mat.entities.Channel;
import com.example.chat_mat.entities.ChannelMembership;
import com.example.chat_mat.entities.Friendship;
import com.example.chat_mat.entities.Role;
import com.example.chat_mat.entities.User;
import com.example.chat_mat.repositories.ChannelMembershipRepository;
import com.example.chat_mat.repositories.ChannelRepository;
import com.example.chat_mat.repositories.FriendshipRepository;
import com.example.chat_mat.repositories.RoleRepository;
import com.example.chat_mat.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final ChannelMembershipRepository channelMembershipRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public ChannelDTO addFriend(Integer currentUserId, Integer friendId) {
        var existingRelation = friendshipRepository.findExistingFriendship(currentUserId, friendId);

        if (existingRelation.isPresent()) {
            Friendship existingFriendship = existingRelation.get();
            var sharedChannel = existingFriendship.getChannel();
            var hasFriendInitiatedFriendship = existingFriendship.getCurrentUser().getId().equals(friendId);
            var hasCurrentUserInitiatedFriendShip = friendshipRepository.findExistingFriendship(friendId, currentUserId).isPresent();

            if (hasFriendInitiatedFriendship && !hasCurrentUserInitiatedFriendShip) {
                var currentUser = userRepository.findById(currentUserId).orElseThrow();
                var friend = userRepository.findById(friendId).orElseThrow();

                ensureMembership(sharedChannel, currentUser);
                ensureMembership(sharedChannel, friend);

                // To ensure availability in user.friendshipsInitiated
                var newFriendship = new Friendship();
                newFriendship.setCurrentUser(currentUser);
                newFriendship.setFriend(friend);
                newFriendship.setChannel(sharedChannel);
                friendshipRepository.save(newFriendship);
            }

            return mapToChannelDTO(existingFriendship.getChannel());

        } else {
            // No friendship at all
            User currentUser = userRepository.findById(currentUserId).orElseThrow();
            User friend = userRepository.findById(friendId).orElseThrow();

            Channel channel = new Channel();
            channel.setIsDeleted(false);
            String channelName = currentUserId < friendId
                    ? (currentUserId + "-" + friendId)
                    : (friendId + "-" + currentUserId);
            channel.setName(channelName);
            channelRepository.save(channel);

            Friendship friendship = new Friendship();
            friendship.setCurrentUser(currentUser);
            friendship.setFriend(friend);
            friendship.setChannel(channel);
            friendshipRepository.save(friendship);

            Role guestRole = roleRepository.findByRoleName("GUEST").orElseThrow();
            addMembership(channel, currentUser, guestRole);
            addMembership(channel, friend, guestRole);

            return mapToChannelDTO(channel);
        }
    }

    private void ensureMembership(Channel channel, User user) {
        boolean alreadyMember = channelMembershipRepository
                .findAll()
                .stream()
                .anyMatch(m -> m.getChannel().getId().equals(channel.getId())
                        && m.getUser().getId().equals(user.getId()));
        if (!alreadyMember) {
            Role guestRole = roleRepository.findByRoleName("GUEST").orElseThrow();
            addMembership(channel, user, guestRole);
        }
    }

    private void addMembership(Channel channel, User user, Role role) {
        ChannelMembership cm = new ChannelMembership();
        cm.setChannel(channel);
        cm.setUser(user);
        cm.setRole(role);
        channelMembershipRepository.save(cm);
    }

    public ChannelDTO mapToChannelDTO(Channel channel) {
        ChannelDTO dto = new ChannelDTO();
        dto.setId(channel.getId());
        dto.setName(channel.getName());
        dto.setIsDeleted(channel.getIsDeleted());
        return dto;
    }
}
