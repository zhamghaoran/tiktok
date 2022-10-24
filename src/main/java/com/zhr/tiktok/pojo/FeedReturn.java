package com.zhr.tiktok.pojo;

import com.zhr.tiktok.utils.Response;
import lombok.Data;

@Data
public class FeedReturn extends Response {
    private Long next_time;
    private video_list[] video_list;

    static public FeedReturn success(String message,video_list[] list,Long next_time) {
        FeedReturn feedReturn = new FeedReturn();
        feedReturn.setStatus_msg(message);
        feedReturn.setStatus_code(0);
        feedReturn.setVideo_list(list);
        feedReturn.setNext_time(next_time);
        return feedReturn;
    }
    static public FeedReturn fail(String message) {
        FeedReturn feedReturn = new FeedReturn();
        feedReturn.setStatus_code(-1);
        feedReturn.setStatus_msg(message);
        return feedReturn;
    }

}
