package com.zhr.tiktok.controller;

import com.zhr.tiktok.parmaVo.ReturnUser;
import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.pojo.focusList;
import com.zhr.tiktok.service.FocusOnService;
import com.zhr.tiktok.utils.MessageInBackground;
import com.zhr.tiktok.utils.Response;
import com.zhr.tiktok.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FocusController {
    @Autowired
    private Token redis;
    @Autowired
    private FocusOnService focusOnService;

    @RequestMapping(value = "/douyin/relation/action/", method = RequestMethod.POST)
    public Response focusOn(String token, String to_user_id, String action_type) {
        User user = redis.CheckToken(token);
        if (user == null) {
            return Response.fail("token无效");
        }
        MessageInBackground res;
        if (action_type.equals("1")) {
            res = focusOnService.Focus(user, to_user_id);
        } else {
            res = focusOnService.unFocus(user, to_user_id);
        }
        if (res.isSuccess()) {
            return Response.success(res.getMessage());
        } else
            return Response.fail(res.getMessage());
    }

    @RequestMapping(value = "/douyin/relation/follow/list/", method = RequestMethod.GET)
    public focusList getFocusList(String token, String user_id) {
        User user = redis.CheckToken(token);
        if (user == null) {
            return focusList.fail("token错误");
        }
        MessageInBackground res = focusOnService.getFocusList(user);
        if (res.isSuccess()) {
            return focusList.success((ReturnUser[]) res.getMap().get("list"));
        } else return (focusList) focusList.fail(res.getMessage());
    }
    @RequestMapping(value = "/douyin/relation/follower/list/",method = RequestMethod.GET)
    public focusList getFollowerList(String token, String user_id) {
        User user = redis.CheckToken(token);
        if (user == null) {
            return focusList.fail("token错误");
        }
        MessageInBackground res = focusOnService.getFollowerList(user);
        if (res.isSuccess()) {
            return focusList.success((ReturnUser[]) res.getMap().get("list"));
        } else return  focusList.fail(res.getMessage());
    }
}
