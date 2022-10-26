package com.zhr.tiktok.controller;

import com.zhr.tiktok.Common.aop.LogAnnotation;
import com.zhr.tiktok.parmaVo.LoginParam;
import com.zhr.tiktok.pojo.LoginReturnParam;
import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.service.LoginService;
import com.zhr.tiktok.utils.MessageInBackground;
import com.zhr.tiktok.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private Token token;

    @RequestMapping(value = "/douyin/user/register/",method = RequestMethod.POST)
    public LoginReturnParam Register (LoginParam loginParam) {
        MessageInBackground login = loginService.Register(loginParam);
        return getLoginReturnParam(login);
    }
    @LogAnnotation(module = "登录")
    @RequestMapping("/douyin/user/login/")
    public LoginReturnParam Login(LoginParam loginParam) {
        MessageInBackground login = loginService.Login(loginParam);
        return getLoginReturnParam(login);
    }

    private LoginReturnParam getLoginReturnParam(MessageInBackground login) {
        LoginReturnParam loginReturnParam = new LoginReturnParam();
        if (!login.isSuccess()) {
            loginReturnParam.setStatus_code(-1);
            loginReturnParam.setStatus_msg(login.getMessage());
        } else {
            Object user = login.getMap().get("user");
            loginReturnParam.setStatus_code(0);
            loginReturnParam.setStatus_msg("success");
            String token1 = token.CreateToken((User) user);
            loginReturnParam.setToken(token1);
            loginReturnParam.setUser_id(((User) user).getId());
        }
        return loginReturnParam;
    }
}
