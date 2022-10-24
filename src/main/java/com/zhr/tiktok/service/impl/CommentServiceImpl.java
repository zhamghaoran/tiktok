package com.zhr.tiktok.service.impl;

import com.zhr.tiktok.mapper.CommentMapper;
import com.zhr.tiktok.pojo.Comment;
import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.service.CommentService;
import com.zhr.tiktok.utils.MessageInBackground;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public MessageInBackground comment(User user, String video_id, String comment_id, String comment_text) {
        Comment comment = new Comment(video_id, null, comment_text, user.getId().toString(), System.currentTimeMillis());
        int insert = commentMapper.insert(comment);
        if (insert > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", comment.getCommentId());
            map.put("user", user);
            map.put("content",comment_text);
            map.put("create_date",comment.getData());
            return MessageInBackground.success(map);
        } else {
            return MessageInBackground.failed("数据库操作失败");
        }
    }

    @Override
    public MessageInBackground delete(User user, String video_id, String comment_id) {
        MessageInBackground res = new MessageInBackground();
        return res;
    }
}
