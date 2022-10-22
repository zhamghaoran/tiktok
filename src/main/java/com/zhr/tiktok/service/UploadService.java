package com.zhr.tiktok.service;

import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.utils.MessageInBackground;

public interface UploadService {
    MessageInBackground uploadVideo(String url, User user, String title);
}
