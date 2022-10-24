package com.zhr.tiktok.pojo;

import com.zhr.tiktok.utils.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReturn extends Response {
    private Map<String ,?> comment;
    public static CommentReturn success(Map<String ,? > res) {
        CommentReturn commentReturn = new CommentReturn();
        commentReturn.setStatus_code(0);
        commentReturn.setStatus_msg("成功");
        commentReturn.setComment(res);
        return commentReturn;
    }
    public static CommentReturn fail(String message) {
        CommentReturn commentReturn = new CommentReturn();
        commentReturn.setStatus_code(-1);
        commentReturn.setStatus_msg(message);
        return commentReturn;
    }
}
