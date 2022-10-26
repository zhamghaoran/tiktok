package com.zhr.tiktok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhr.tiktok.mapper.FollowMapper;
import com.zhr.tiktok.pojo.follow;
import com.zhr.tiktok.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class followServiceImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Override
    public boolean select(Integer userid, String videoId) {
        LambdaQueryWrapper<follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(follow::getFromm,userid);
        queryWrapper.eq(follow::getToo,Integer.parseInt(videoId));
        follow follow = followMapper.selectOne(queryWrapper);
        return follow != null;
    }
}
