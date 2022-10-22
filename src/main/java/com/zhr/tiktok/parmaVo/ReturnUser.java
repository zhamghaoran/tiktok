package com.zhr.tiktok.parmaVo;

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
    @JsonAlias("name")
    private String username;
    @JsonAlias("follow_count")
    private Integer follow;
    @JsonAlias("follower_count")
    private Integer follower;
    private boolean is_follow;
}
