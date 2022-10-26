package com.zhr.tiktok.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhr.tiktok.mapper.UserMapper;
import com.zhr.tiktok.parmaVo.LoginParam;
import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.service.LoginService;
import com.zhr.tiktok.utils.MessageInBackground;
import com.zhr.tiktok.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Token redis;

    static String salt = "123456";

    @Override
    public MessageInBackground Register(LoginParam loginParam) {
        if (StringUtils.isEmpty(loginParam)) {
            return MessageInBackground.failed("参数异常");
        }
        String newPassword = DigestUtils.md5DigestAsHex((loginParam.getPassword() + salt).getBytes());
        Map<String, User> map = new HashMap<>();
        User user = new User(1, loginParam.getUsername(), newPassword,0,0);
        int insert = userMapper.insert(user);
        if (insert > 0) {
            map.put("user", user);
            return MessageInBackground.success(map);
        } else {
            return MessageInBackground.failed("数据库错误");
        }
    }

    @Override
    public MessageInBackground Login(LoginParam loginParam) {
        if (StringUtils.isEmpty(loginParam)) {
            return MessageInBackground.failed("参数异常");
        }
        String newPassword = DigestUtils.md5DigestAsHex((loginParam.getPassword() + salt).getBytes());
        User user = null;
        user = redis.CheckLogin(new User(0,loginParam.getUsername(),newPassword,0,0));
        if (user != null ){
            Map<String ,User> map = new HashMap<>();
            map.put("user",user);
            return MessageInBackground.success(map);
        } else {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUsername, loginParam.getUsername());
            queryWrapper.eq(User::getPassword, newPassword);
            user = userMapper.selectOne(queryWrapper);
        }
        if (user == null) {
            return MessageInBackground.failed("账号密码错误");
        }
        else {
            Map<String ,User> map = new HashMap<>();
            map.put("user",user);
            return MessageInBackground.success(map);
        }
    }
}
