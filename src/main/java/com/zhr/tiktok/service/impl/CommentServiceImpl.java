package com.zhr.tiktok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhr.tiktok.mapper.CommentMapper;
import com.zhr.tiktok.mapper.UserMapper;
import com.zhr.tiktok.parmaVo.ReturnUser;
import com.zhr.tiktok.pojo.Comment;
import com.zhr.tiktok.pojo.CommentList;
import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.pojo.comment_list;
import com.zhr.tiktok.service.CommentService;
import com.zhr.tiktok.utils.MessageInBackground;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zhr.tiktok.service.impl.FavoriteServiceImpl.getReturnUser;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;

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
        LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper.eq(Comment::getCommentId,comment_id);
        commentLambdaQueryWrapper.eq(Comment::getVideoId,video_id);
        int delete = commentMapper.delete(commentLambdaQueryWrapper);
        if (delete > 0) {
            return MessageInBackground.success(null);
        } else {
            return MessageInBackground.failed("数据库操作失败");
        }
    }
    public ReturnUser findUser(String userId) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getId,userId);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        return getReturnUser(user);
    }
    public CommentList getList(List<Comment> comments) {
        CommentList commentList = new CommentList();
        comment_list[] comment_list = new comment_list[comments.size()];
        for (int i = 0;i < comments.size();i ++) {
            com.zhr.tiktok.pojo.comment_list comment_list1 = new comment_list();
            comment_list1.setId(Integer.valueOf(comments.get(i).getVideoId()));
            comment_list1.setUser(findUser(comments.get(i).getUserId()));
            comment_list1.setContent(comments.get(i).getCommentDetail());
            comment_list1.setCreate_time(comments.get(i).getData());
            comment_list[i] = comment_list1;
        }
        commentList.setComment_list(comment_list);
        return commentList;
    }
    @Override
    public MessageInBackground getCommentList(String video_id) {
        LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper.eq(Comment::getVideoId,video_id);
        List<Comment> comments = commentMapper.selectList(commentLambdaQueryWrapper);
        CommentList list = getList(comments);
        Map<String ,CommentList> map = new HashMap<>();
        map.put("CommentList",list);
        return MessageInBackground.success(map);
    }
}
