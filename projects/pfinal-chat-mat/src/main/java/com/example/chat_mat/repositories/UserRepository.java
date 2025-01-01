package com.example.chat_mat.repositories;

import com.example.chat_mat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    List<User> findByEmailContaining(String email);
    Optional<User> findUserByEmailAndPassword(String email, String password);
}