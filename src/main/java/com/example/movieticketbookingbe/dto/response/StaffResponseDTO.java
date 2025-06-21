package com.example.movieticketbookingbe.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // cần thiết để gọi constructor 8 tham sốAdd commentMore actions
@NoArgsConstructor
public class StaffResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String username;
    private String fullName;
    private String dateOfBirth;
}
