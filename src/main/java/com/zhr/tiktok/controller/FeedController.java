package com.zhr.tiktok.controller;

import com.qiniu.util.StringUtils;
import com.zhr.tiktok.pojo.FeedReturn;
import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.service.FeedService;
import com.zhr.tiktok.utils.MessageInBackground;
import com.zhr.tiktok.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FeedController {
    @Autowired
    private FeedService feedService;
    @Autowired
    private Token tokenn;
    @RequestMapping("/douyin/feed")
    public FeedReturn Feed(String latest_time, String token) {
        if (StringUtils.isNullOrEmpty(latest_time) || latest_time.equals("0")) {
            latest_time = String.valueOf(System.currentTimeMillis());
        }
        User user = this.tokenn.CheckToken(token);
        if (user == null) {
            FeedReturn feedReturn = new FeedReturn();
            feedReturn.setStatus_code(-1);
            feedReturn.setStatus_msg("token无效");
            return  feedReturn;
        }
        MessageInBackground select = feedService.select(latest_time, user);
        if (!select.isSuccess()) {
            return FeedReturn.fail(select.getMessage());
        }
        System.out.println(select.getMap().get("feed"));
        return (FeedReturn) select.getMap().get("feed");
    }
}
