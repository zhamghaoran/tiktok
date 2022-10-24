package com.zhr.tiktok.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhr.tiktok.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
