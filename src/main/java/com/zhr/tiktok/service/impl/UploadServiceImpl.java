package com.zhr.tiktok.service.impl;

import com.zhr.tiktok.mapper.VideoMapper;
import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.pojo.Video;
import com.zhr.tiktok.service.UploadService;
import com.zhr.tiktok.utils.MessageInBackground;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    private VideoMapper videoMapper;

    @Override
    public MessageInBackground uploadVideo(String url, User user, String title) {
        Video video = new Video(0, user.getUsername(), url, null, 0, title,System.currentTimeMillis());
        int insert = videoMapper.insert(video);
        if (insert > 0) {
            return MessageInBackground.success(null);
        }
        else  {
            return MessageInBackground.failed("数据库错误");
        }
    }
}
