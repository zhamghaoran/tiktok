package com.zhr.tiktok.service;


import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.utils.MessageInBackground;

public interface FavoriteAction {

    MessageInBackground action(User user, String video_id);

    MessageInBackground delete(User user, String video_id);

    MessageInBackground getList(User user);
}
