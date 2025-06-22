package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.dto.user.UserCreateDTO;
import com.example.movieticketbookingbe.dto.user.UserPatchDTO;
import com.example.movieticketbookingbe.mapper.UserMapper;
import com.example.movieticketbookingbe.model.User;
import com.example.movieticketbookingbe.repository.UserRepository;
import com.example.movieticketbookingbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, UserCreateDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setFullName(userDTO.getFullName());
        existingUser.setDateOfBirth(userDTO.getDateOfBirth());

        // 👇 Chỉ update password nếu được gửi lên
        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            existingUser.setPassword(userDTO.getPassword());
        }

        // 👇 Chỉ update role nếu được gửi lên
        if (userDTO.getRole() != null && !userDTO.getRole().isBlank()) {
            existingUser.setRole(User.UserRole.valueOf(userDTO.getRole().toUpperCase()));
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> getAllCustomers(User.UserRole role) {
        return userRepository.findByRoleAndIsActiveTrue(role);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User patchUser(Long id, UserPatchDTO patchDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (patchDTO.getFullName() != null) user.setFullName(patchDTO.getFullName());
        if (patchDTO.getEmail() != null) user.setEmail(patchDTO.getEmail());
        if (patchDTO.getPhoneNumber() != null) user.setPhoneNumber(patchDTO.getPhoneNumber());
        if (patchDTO.getAddress() != null) user.setAddress(patchDTO.getAddress());
        if (patchDTO.getIsActive() != null) user.setIsActive(patchDTO.getIsActive());
        if (patchDTO.getRole() != null) user.setRole(User.UserRole.valueOf(patchDTO.getRole().toUpperCase()));
        return userRepository.save(user);
    }
}