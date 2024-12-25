package com.example.chat_mat.repositories;

import com.example.chat_mat.entities.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
    @Query("""
            SELECT f
            FROM Friendship f
            WHERE (f.currentUser.id = :userId2 AND f.friend.id = :userId1)
            """)
    Optional<Friendship> findExistingFriendship(Integer userId1, Integer userId2);
}
