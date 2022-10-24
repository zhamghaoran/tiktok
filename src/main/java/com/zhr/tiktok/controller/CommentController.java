package com.zhr.tiktok.controller;


import com.zhr.tiktok.mapper.CommentMapper;
import com.zhr.tiktok.pojo.CommentReturn;
import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.service.CommentService;
import com.zhr.tiktok.utils.MessageInBackground;
import com.zhr.tiktok.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private Token redis;

    @RequestMapping("/douyin/comment/action/")
    public CommentReturn CommentAction(String token, String video_id, String action_id, String comment_text, String comment_id) {
        User user = redis.CheckToken(token);
        if (user == null) {
            return CommentReturn.fail("token无效");
        }
        MessageInBackground res;
        if (action_id.equals("1")) {
            res = commentService.comment(user, video_id, comment_id, comment_text);
            if (!res.isSuccess()) {
                return CommentReturn.fail(res.getMessage());
            } else {
                Map<String, ?> map = res.getMap();
                Map<String ,Map<String ,?>> mapp = new HashMap<>();
                mapp.put("0",map);
                return CommentReturn.success(mapp);
            }
        } else {
            res = commentService.delete(user, video_id, comment_id);
            if (res.isSuccess()) {
                Map<String ,?> map = new HashMap<>();
                return CommentReturn.success(map);
            }
            else {
                return CommentReturn.fail(res.getMessage());
            }
        }
    }
}
