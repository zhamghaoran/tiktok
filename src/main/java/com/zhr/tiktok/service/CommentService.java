package com.zhr.tiktok.service;

import com.zhr.tiktok.utils.MessageInBackground;
import com.zhr.tiktok.pojo.*;

public interface CommentService {
    MessageInBackground comment(User user,String video_id,String comment_id,String comment_text);

    MessageInBackground delete(User user, String video_id, String comment_id);

    MessageInBackground getCommentList(String video_id);
}
