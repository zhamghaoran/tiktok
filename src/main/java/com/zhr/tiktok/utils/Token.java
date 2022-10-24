package com.zhr.tiktok.utils;


import com.alibaba.fastjson.JSON;
import com.zhr.tiktok.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.Random;


@Component
public class Token {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    static Integer salt = 123456;

    public String CreateToken(User user) {
        long l = System.currentTimeMillis() % new Random().nextInt(100) + 1;
        l += salt + new Random().nextInt(10000010);
        redisTemplate.opsForValue().set(String.valueOf(l), JSON.toJSONString(user));
        redisTemplate.expire(String.valueOf(l), Duration.ofMinutes(5));
        return String.valueOf(l);
    }

    public User CheckToken(String token) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(token))) {
            return null;
        }
        String s = redisTemplate.opsForValue().get(token);
        return Objects.requireNonNull(JSON.parseObject(s)).toJavaObject(User.class);
    }
}

