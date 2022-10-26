package com.zhr.tiktok.utils;


import com.alibaba.fastjson.JSON;
import com.zhr.tiktok.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.time.Duration;
import java.util.Objects;
import java.util.Random;


@Component
public class Token {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    static String  salt = "adfhjikadhahahdsrg";

    public String CreateToken(User user) {
        String s = DigestUtils.md5DigestAsHex((user.getUsername() + user.getPassword() + salt).getBytes());
        redisTemplate.opsForValue().set(s, JSON.toJSONString(user));
        redisTemplate.expire(s, Duration.ofMinutes(10));
        return s;
    }

    public User CheckToken(String token) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(token))) {
            return null;
        }
        String s = redisTemplate.opsForValue().get(token);
        return Objects.requireNonNull(JSON.parseObject(s)).toJavaObject(User.class);
    }
    public User CheckLogin(User user) {
        String s = DigestUtils.md5DigestAsHex((user.getUsername() + user.getPassword() + salt).getBytes());
        String s1 = redisTemplate.opsForValue().get(s);
        return s1 != null ?Objects.requireNonNull(JSON.parseObject(s1)).toJavaObject(User.class) : null;
    }
}

