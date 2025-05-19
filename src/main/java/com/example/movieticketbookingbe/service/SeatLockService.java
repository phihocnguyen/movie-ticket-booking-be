package com.example.movieticketbookingbe.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatLockService {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String LOCK_PREFIX = "seat:lock:";
    private static final long LOCK_TIMEOUT = 5;

    public boolean lockSeat(Long seatId, Long userId) {
        String lockKey = LOCK_PREFIX + seatId;
        Boolean acquired = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, userId.toString(), LOCK_TIMEOUT, TimeUnit.MINUTES);

        return Boolean.TRUE.equals(acquired);
    }

    public boolean unlockSeat(Long seatId, Long userId) {
        String lockKey = LOCK_PREFIX + seatId;
        Object currentUserId = redisTemplate.opsForValue().get(lockKey);

        if (currentUserId != null && currentUserId.toString().equals(userId.toString())) {
            return Boolean.TRUE.equals(redisTemplate.delete(lockKey));
        }
        return false;
    }

    public boolean isSeatLocked(Long seatId) {
        String lockKey = LOCK_PREFIX + seatId;
        return Boolean.TRUE.equals(redisTemplate.hasKey(lockKey));
    }

    public Long getLockOwner(Long seatId) {
        String lockKey = LOCK_PREFIX + seatId;
        Object userId = redisTemplate.opsForValue().get(lockKey);
        return userId != null ? Long.parseLong(userId.toString()) : null;
    }
}