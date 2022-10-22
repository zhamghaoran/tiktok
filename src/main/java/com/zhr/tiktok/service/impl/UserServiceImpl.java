package com.zhr.tiktok.service.impl;

import com.zhr.tiktok.mapper.UserMapper;
import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.service.UserService;
import com.zhr.tiktok.utils.MessageInBackground;
import com.zhr.tiktok.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    Token token1;

    @Override
    public MessageInBackground getUser(String token, String user_id) {
        User user = token1.CheckToken(token);
        if (user == null) {
            return MessageInBackground.failed("token或userId错误");
        }
        Map<String ,User> map = new HashMap<>();
        map.put("user",user);
        return MessageInBackground.success(map);
    }
}
