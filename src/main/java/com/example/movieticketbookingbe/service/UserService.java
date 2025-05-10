package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByPhoneNumber(String phoneNumber);

    List<User> getAllUsers();

    List<User> getActiveUsers();

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);
}