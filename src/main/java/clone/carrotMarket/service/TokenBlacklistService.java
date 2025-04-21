package clone.carrotMarket.service;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class TokenBlacklistService {

    private final RedisTemplate<String, String > redisTemplate;

    //블랙리스트 저장
    public void blacklistToken(String token, long expiration) {
        redisTemplate.opsForValue().set(token, "blacklisted", expiration, TimeUnit.MICROSECONDS);
    }

    //블랙리스트에 포함되었는가
    public boolean isBlacklisted(String token) {
        return redisTemplate.hasKey(token);
    }
}
