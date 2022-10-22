package com.zhr.tiktok.utils;


import com.alibaba.fastjson.JSON;
import com.zhr.tiktok.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;


@Component
public class Token {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    static Integer salt = 123456;

    public String CreateToken(User user) {
        long l = System.currentTimeMillis();
        l += salt;
        redisTemplate.opsForValue().set(String.valueOf(l), JSON.toJSONString(user));
        redisTemplate.expire(String.valueOf(l), Duration.ofMinutes(5));
        return String.valueOf(l);
    }

    public User CheckToken(String token) {
        String s = redisTemplate.opsForValue().get(token);
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        return JSON.parseObject(s).toJavaObject(User.class);
    }
}

