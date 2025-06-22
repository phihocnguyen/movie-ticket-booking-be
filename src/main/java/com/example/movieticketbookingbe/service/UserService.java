package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.dto.user.UserCreateDTO;
import com.example.movieticketbookingbe.dto.user.UserPatchDTO;
import com.example.movieticketbookingbe.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);

    User updateUser(Long id, UserCreateDTO user);

    void deleteUser(Long id);

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByPhoneNumber(String phoneNumber);


    List<User> getAllCustomers(User.UserRole role);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

    User patchUser(Long id, UserPatchDTO patchDTO);
}