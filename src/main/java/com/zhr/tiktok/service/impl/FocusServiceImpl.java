package com.zhr.tiktok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.zhr.tiktok.mapper.FocusMapper;
import com.zhr.tiktok.mapper.UserMapper;
import com.zhr.tiktok.parmaVo.ReturnUser;
import com.zhr.tiktok.pojo.FocusRelation;
import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.pojo.focusList;
import com.zhr.tiktok.service.FocusOnService;
import com.zhr.tiktok.utils.MessageInBackground;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FocusServiceImpl implements FocusOnService {
    @Autowired
    private FocusMapper focusMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public MessageInBackground Focus(User user, String to_user_id) {
        FocusRelation focusRelation = new FocusRelation(user.getId().toString(), to_user_id);
        int insert = focusMapper.insert(focusRelation);
        if (insert > 0) {
            return MessageInBackground.success(null);
        } else return MessageInBackground.failed("数据库操作失败");
    }
    @Override
    public MessageInBackground unFocus(User user, String to_user_id) {
        int delete = focusMapper.delete(new LambdaQueryWrapper<FocusRelation>().eq(FocusRelation::getFromm, user.getId().toString()).eq(FocusRelation::getToo, to_user_id));
        if (delete > 0 )  {
            return MessageInBackground.success(null);
        } else {
            return MessageInBackground.failed("数据库操作失败");
        }
    }

    @Override
    public MessageInBackground getFocusList(User user) {
        LambdaQueryWrapper<FocusRelation> focusRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        focusRelationLambdaQueryWrapper.eq(FocusRelation::getFromm,user.getId().toString());
        List<FocusRelation> focusRelations = focusMapper.selectList(focusRelationLambdaQueryWrapper);
        ReturnUser[] returnUsers = new ReturnUser[focusRelations.size()];
        for (int i = 0;i < focusRelations.size();i ++) {
            User user1 = userMapper.selectById(Integer.parseInt(focusRelations.get(i).getToo()));
            ReturnUser returnUser = new ReturnUser(user1.getId(),user1.getUsername(),user1.getFollow(),user1.getFollower(),true);
            returnUsers[i] = returnUser;
        }
        Map<String ,ReturnUser[]> map = new HashMap<>();
        map.put("list",returnUsers);
        return MessageInBackground.success(map);
    }

    @Override
    public MessageInBackground getFollowerList(User user) {
        LambdaQueryWrapper<FocusRelation> focusRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        focusRelationLambdaQueryWrapper.eq(FocusRelation::getToo,user.getId().toString());
        List<FocusRelation> focusRelations = focusMapper.selectList(focusRelationLambdaQueryWrapper);
        ReturnUser[] returnUsers = new ReturnUser[focusRelations.size()];
        for (int i = 0;i < focusRelations.size();i ++) {
            User user1 = userMapper.selectById(Integer.parseInt(focusRelations.get(i).getFromm()));
            ReturnUser returnUser = new ReturnUser(user1.getId(),user1.getUsername(),user1.getFollow(),user1.getFollower(),true);
            returnUsers[i] = returnUser;
        }
        Map<String ,ReturnUser[]> map = new HashMap<>();
        map.put("list",returnUsers);
        return MessageInBackground.success(map);
    }
}
