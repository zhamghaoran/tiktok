package com.zhr.tiktok.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhr.tiktok.pojo.FocusRelation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("Focus")
public interface FocusMapper extends BaseMapper<FocusRelation> {
}
