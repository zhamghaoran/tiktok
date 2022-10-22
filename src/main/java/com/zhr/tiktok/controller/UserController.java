package com.zhr.tiktok.controller;

import com.zhr.tiktok.parmaVo.ReturnUser;
import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.pojo.UserReturn;
import com.zhr.tiktok.service.UserService;
import com.zhr.tiktok.service.FollowService;
import com.zhr.tiktok.utils.MessageInBackground;
import com.zhr.tiktok.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FollowService followService;

    @RequestMapping("/douyin/user/")
    public Response getUser(String token,String user_id) {
        MessageInBackground user = userService.getUser(token, user_id);
        Map<Integer,ReturnUser> map = new HashMap<>();
        if (!user.isSuccess()) {
            map.put(1,null);
            return new UserReturn(map,-1,user.getMessage());
        }
        User user1 = (User) user.getMap().get("user");
        boolean res = followService.select(user1.getId(),user_id);
        ReturnUser returnUser = new ReturnUser(user1.getId(), user1.getUsername(), user1.getFollow(), user1.getFollower(),res);
        map.put(0,returnUser);
        return new UserReturn(map,0,"成功");
    }
}
