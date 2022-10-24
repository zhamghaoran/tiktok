package com.zhr.tiktok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiniu.rtc.model.MergeParam;
import com.zhr.tiktok.mapper.UserMapper;
import com.zhr.tiktok.mapper.VideoMapper;
import com.zhr.tiktok.parmaVo.ReturnUser;
import com.zhr.tiktok.pojo.FeedReturn;
import com.zhr.tiktok.pojo.Video;
import com.zhr.tiktok.pojo.video_list;
import com.zhr.tiktok.service.FeedService;
import com.zhr.tiktok.service.FollowService;
import com.zhr.tiktok.utils.MessageInBackground;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zhr.tiktok.pojo.*;
import sun.rmi.server.MarshalOutputStream;

import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class FeedServiceImpl implements FeedService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FollowService followService;

    public ReturnUser selectUser(String user, Integer id) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername, user);
        User user1 = userMapper.selectOne(userLambdaQueryWrapper);
        ReturnUser user2 = new ReturnUser();
        user2.setFollow_count(user1.getFollow());
        user2.setFollower_count(user1.getFollower());
        user2.setName(user1.getUsername());
        user2.setId(user1.getId());
        user2.set_follow(followService.select(user1.getId(), String.valueOf(id)));
        return user2;

    }


    public video_list getVideoList(ReturnUser returnUser, Video video) {
        video_list video_list = new video_list();
        video_list.setAuthor(returnUser);
        video_list.setId(video.getId());
        video_list.setTitle(video.getTitle());
        video_list.setPlay_url(video.getUrl());
        video_list.setId(video.getId());
        video_list.setComment_count(video.getCommentCount());
        video_list.setFavorite_count(video.getFavoriteCount());
        video_list.setCover_url(video.getCoverUrl());
        return video_list;

    }

    @Override
    public MessageInBackground select(String latest_time, User user) {
        Long data = Long.parseLong(latest_time);
        LambdaQueryWrapper<Video> videoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        videoLambdaQueryWrapper.lt(Video::getCreateTime, data).orderByDesc(Video::getCreateTime).last("limit 1");
        Video videos = videoMapper.selectOne(videoLambdaQueryWrapper);
        if (videos == null) {
            return MessageInBackground.failed("查找数据失败");
        }
        FeedReturn feedReturn = new FeedReturn();
        String username = videos.getUsername();
        ReturnUser returnUser = selectUser(username, user.getId());
        if (returnUser == null) {
            return MessageInBackground.failed("查找数据失败");
        }
        feedReturn.setNext_time(videos.getCreateTime());
        video_list videoList = getVideoList(returnUser, videos);
        video_list[] v = new video_list[1];
        v[0] = videoList;
        feedReturn.setVideo_list(v);
        feedReturn.setStatus_code(0);
        feedReturn.setStatus_msg("success");
        Map<String ,Object> map = new HashMap<>();
        map.put("feed",feedReturn);
        return MessageInBackground.success(map);
    }
}
