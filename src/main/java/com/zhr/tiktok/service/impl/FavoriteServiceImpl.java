package com.zhr.tiktok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhr.tiktok.mapper.FavoriteMapper;
import com.zhr.tiktok.mapper.UserMapper;
import com.zhr.tiktok.mapper.VideoMapper;
import com.zhr.tiktok.parmaVo.ReturnUser;
import com.zhr.tiktok.pojo.Favorite;
import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.pojo.Video;
import com.zhr.tiktok.pojo.video_list;
import com.zhr.tiktok.service.FavoriteAction;
import com.zhr.tiktok.service.FollowService;
import com.zhr.tiktok.utils.MessageInBackground;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteAction {
    @Autowired
    UserMapper userMapper;
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    FavoriteMapper favoriteMapper;
    @Autowired
    FollowService followService;

    @Override
    public MessageInBackground action(User user, String video_id) {
        Favorite favorite = new Favorite(user.getId().toString(), video_id);
        int insert = favoriteMapper.insert(favorite);
        if (insert > 0) {
            return MessageInBackground.success(null);
        } else {
            return MessageInBackground.failed("数据库操作失败");
        }
    }

    @Override
    public MessageInBackground delete(User user, String video_id) {
        LambdaQueryWrapper<Favorite> favoriteLambdaQueryWrapper = new LambdaQueryWrapper<>();
        favoriteLambdaQueryWrapper.eq(Favorite::getUserid, user.getId().toString());
        favoriteLambdaQueryWrapper.eq(Favorite::getVideoId, video_id);
        int delete = favoriteMapper.delete(favoriteLambdaQueryWrapper);
        if (delete > 0) {
            return MessageInBackground.success(null);
        } else {
            return MessageInBackground.failed("删除失败");
        }
    }
    public List<Video> findVideo(List<Favorite> favorites) {
        List<Video> videos = new ArrayList<>();
        for(Favorite i : favorites) {
            LambdaQueryWrapper<Video> videoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            videoLambdaQueryWrapper.eq(Video::getId,Integer.parseInt(i.getVideoId()));
            Video video = videoMapper.selectOne(videoLambdaQueryWrapper);
            videos.add(video);
        }
        return videos;
    }
    public static ReturnUser getReturnUser(User user) {
        ReturnUser returnUser = new ReturnUser();
        returnUser.setId(user.getId());
        returnUser.setFollower_count(user.getFollower());
        returnUser.setFollow_count(user.getFollow());
        returnUser.setName(user.getUsername());
        return  returnUser;
    }
    public video_list getOneVideoList(ReturnUser another,Video video,User host) {
        video_list res = new video_list();
        res.setAuthor(another);
        res.setTitle(video.getTitle());
        res.setCover_url(video.getCoverUrl());
        res.setPlay_url(video.getUrl());
        res.setId(video.getId());
        res.setFavorite_count(video.getFavoriteCount());
        res.setComment_count(video.getCommentCount());
        res.set_favorite(followService.select(host.getId(), video.getId().toString()));
        return res;
    }
    public video_list[] getlist(List<Video> videos,User host) {
        video_list[] res =  new video_list[videos.size()];
        for(int i = 0;i < videos.size();i ++) {
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getUsername,videos.get(i).getUsername());
            User user = userMapper.selectOne(userLambdaQueryWrapper);
            ReturnUser returnUser = getReturnUser(user);
            video_list oneVideoList = getOneVideoList(returnUser, videos.get(i),host);
            res[i] = oneVideoList;
        }
        return res;
    }
    @Override
    public MessageInBackground getList(User user) {
        LambdaQueryWrapper<Favorite> favoriteLambdaQueryWrapper = new LambdaQueryWrapper<>();
        favoriteLambdaQueryWrapper.eq(Favorite::getUserid, user.getId().toString());
        List<Favorite> favorites = favoriteMapper.selectList(favoriteLambdaQueryWrapper);
        List<Video> video = findVideo(favorites);
        video_list[] getlist = getlist(video,user);
        Map<String, video_list[]> map = new HashMap<>();
        map.put("favorite", getlist);
        return MessageInBackground.success(map);
    }
}
