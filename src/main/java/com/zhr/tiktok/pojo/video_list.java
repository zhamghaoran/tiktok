package com.zhr.tiktok.pojo;

import com.zhr.tiktok.parmaVo.ReturnUser;
import lombok.Data;

@Data
public class video_list {
    private Integer id;
    private ReturnUser author;
    private String play_url;
    private String cover_url;
    private Integer favorite_count;
    private Integer comment_count;
    private boolean is_favorite;
    private String title;
}
