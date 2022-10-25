package com.zhr.tiktok.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String videoId;
    @TableField("id")
    private String commentId;
    private String commentDetail;
    private String userId;
    private Long data;
}
