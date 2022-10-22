package com.zhr.tiktok.service;

import com.zhr.tiktok.utils.MessageInBackground;

public interface UserService
{
    MessageInBackground getUser(String token, String user_id);
}
