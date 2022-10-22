package com.zhr.tiktok.service;

import com.zhr.tiktok.parmaVo.LoginParam;
import com.zhr.tiktok.utils.MessageInBackground;

public interface LoginService {
    MessageInBackground Register(LoginParam loginParam);

    MessageInBackground Login (LoginParam loginParam);

}
