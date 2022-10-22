package com.zhr.tiktok.service;

import com.zhr.tiktok.pojo.FeedReturn;
import com.zhr.tiktok.pojo.*;
import com.zhr.tiktok.utils.MessageInBackground;

public interface FeedService {
    MessageInBackground select(String latest_time, User user);
}
