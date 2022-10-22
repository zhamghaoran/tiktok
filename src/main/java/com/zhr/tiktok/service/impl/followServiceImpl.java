package com.zhr.tiktok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhr.tiktok.mapper.FollowMapper;
import com.zhr.tiktok.pojo.follow;
import com.zhr.tiktok.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class followServiceImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Override
    public boolean select(Integer id, String user_id) {
        LambdaQueryWrapper<follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(follow::getFromm,id);
        queryWrapper.eq(follow::getToo,Integer.parseInt(user_id));
        follow follow = followMapper.selectOne(queryWrapper);
        return follow != null;
    }
}
