package com.example.movieticketbookingbe.dto.uservoucher;

import lombok.Data;
 
@Data
public class UserVoucherCreateDTO {
    private Long userId;
    private Long voucherId;
} 