package com.zhr.tiktok.pojo;

import com.zhr.tiktok.parmaVo.ReturnUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.PrimitiveIterator;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class comment_list {
    private Integer id;
    private String content;
    private Long create_time;
    private ReturnUser user;
}
