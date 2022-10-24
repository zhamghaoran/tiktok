package com.zhr.tiktok.parmaVo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnUser {
    private Integer id;
    @TableField("username")
    private String name;
    @TableField("follow")
    private Integer follow_count;
    @TableField("follower")
    private Integer follower_count;
    private boolean is_follow;
}
