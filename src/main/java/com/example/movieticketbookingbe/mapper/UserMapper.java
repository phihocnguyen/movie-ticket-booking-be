package com.example.movieticketbookingbe.mapper;
import com.example.movieticketbookingbe.dto.user.UserCreateDTO;
import com.example.movieticketbookingbe.dto.user.UserDTO;
import com.example.movieticketbookingbe.model.User;
import com.example.movieticketbookingbe.model.User.UserRole;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole() != null ? user.getRole().name() : null);
        dto.setIsActive(user.getIsActive());
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getFullName());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setBookings(user.getBookings() != null ? user.getBookings().stream().map(BookingMapper::toDTO).collect(Collectors.toList()) : null);
        dto.setPayments(user.getPayments() != null ? user.getPayments().stream().map(PaymentMapper::toDTO).collect(Collectors.toList()) : null);
        return dto;
    }

    public static User toEntity(UserCreateDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(dto.getRole() != null ? UserRole.valueOf(dto.getRole()) : null);
        user.setUsername(dto.getUsername());
        user.setFullName(dto.getFullName());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setIsActive(true);
        return user;
    }
} 