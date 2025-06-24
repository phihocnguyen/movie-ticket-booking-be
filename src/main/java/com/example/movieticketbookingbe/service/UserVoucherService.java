package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.UserVoucher;
import java.util.List;
 
public interface UserVoucherService {
    UserVoucher assignVoucherToUser(Long userId, Long voucherId);
    List<UserVoucher> getVouchersByUser(Long userId);
    UserVoucher markVoucherAsUsed(Long id);
} 