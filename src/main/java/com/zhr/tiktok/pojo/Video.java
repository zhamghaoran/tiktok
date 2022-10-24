package com.zhr.tiktok.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String url;
    @TableField("cover_url")
    private String coverUrl;
    @TableField("favorite_count")
    private Integer favoriteCount;
    private Integer commentCount;
    private String title;
    private Long createTime;

}
