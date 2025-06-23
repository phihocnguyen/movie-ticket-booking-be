package com.example.movieticketbookingbe.service.impl;

import com.example.movieticketbookingbe.model.UserVoucher;
import com.example.movieticketbookingbe.repository.UserVoucherRepository;
import com.example.movieticketbookingbe.service.UserVoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserVoucherServiceImpl implements UserVoucherService {
    private final UserVoucherRepository userVoucherRepository;

    @Override
    public UserVoucher assignVoucherToUser(Long userId, Long voucherId) {
        UserVoucher userVoucher = new UserVoucher();
        userVoucher.setUserId(userId);
        userVoucher.setVoucherId(voucherId);
        userVoucher.setIsUsed(false);
        return userVoucherRepository.save(userVoucher);
    }

    @Override
    public List<UserVoucher> getVouchersByUser(Long userId) {
        return userVoucherRepository.findByUserId(userId);
    }

    @Override
    public UserVoucher markVoucherAsUsed(Long id) {
        UserVoucher userVoucher = userVoucherRepository.findById(id).orElse(null);
        if (userVoucher != null) {
            userVoucher.setIsUsed(true);
            return userVoucherRepository.save(userVoucher);
        }
        return null;
    }
} 