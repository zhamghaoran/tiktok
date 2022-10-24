package com.zhr.tiktok.controller;

import com.zhr.tiktok.pojo.FeedReturn;
import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.pojo.video_list;
import com.zhr.tiktok.service.FavoriteAction;
import com.zhr.tiktok.utils.MessageInBackground;
import com.zhr.tiktok.utils.Response;
import com.zhr.tiktok.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FavoriteController {
    @Autowired
    private Token tokenCheck;
    @Autowired
    private FavoriteAction favoriteAction;

    @RequestMapping(value = "/douyin/favorite/action/",method = RequestMethod.POST)
    public Response favoriteAction(String token, String video_id, String action_type) {
        User user = tokenCheck.CheckToken(token);
        if (user == null) {
            return Response.fail("token 无效");
        }
        MessageInBackground result;
        if (action_type.equals("1")) {
            result = favoriteAction.action(user, video_id);
        } else {
            result = favoriteAction.delete(user, video_id);
        }
        if (result.isSuccess()) {
            return Response.success("操作成功");
        } else {
            return  Response.fail("操作失败");
        }
    }
    @RequestMapping(value = "/douyin/favorite/list/",method = RequestMethod.GET)
    public FeedReturn favoriteList(String user_id, String token) {
        User user = tokenCheck.CheckToken(token);
        if (user == null) {
            return FeedReturn.fail("token错误");
        }
        MessageInBackground list = favoriteAction.getList(user);
        if (list.isSuccess()) {
            return FeedReturn.success(list.getMessage(),(video_list[]) list.getMap().get("favorite"),null);
        }
        else {
            return  FeedReturn.fail(list.getMessage());
        }

    }
}
