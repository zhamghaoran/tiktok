package com.zhr.tiktok.service;


import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.utils.MessageInBackground;

public interface FocusOnService {
    MessageInBackground Focus(User user, String to_user_id);

    MessageInBackground unFocus(User user, String to_user_id);

    MessageInBackground getFocusList(User user);

    MessageInBackground getFollowerList(User user);
}
