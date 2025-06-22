package com.example.movieticketbookingbe.dto.theaterowner;

import com.example.movieticketbookingbe.dto.user.UserDTO;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TheaterOwnerDTO {
    private Long id;
    private UserDTO user;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}