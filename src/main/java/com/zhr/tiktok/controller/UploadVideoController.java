package com.zhr.tiktok.controller;

import com.zhr.tiktok.pojo.User;
import com.zhr.tiktok.service.UploadService;
import com.zhr.tiktok.utils.MessageInBackground;
import com.zhr.tiktok.utils.QiniuUtils;
import com.zhr.tiktok.utils.Response;
import com.zhr.tiktok.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController

public class UploadVideoController {
    @Autowired
    private QiniuUtils qiniuUtils;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private Token Token;
    @RequestMapping(value = "/douyin/publish/action/",method = RequestMethod.POST)
    public Response up (MultipartFile multipartFile,String token,String title) {
        User user = Token.CheckToken(token);
        if (user == null) {
            return Response.fail("无效的Token");
        }
        boolean upload = qiniuUtils.upload(multipartFile, title + multipartFile.getOriginalFilename());
        if (!upload) {
            return Response.fail("上传视频失败");
        }
        MessageInBackground messageInBackground = uploadService.uploadVideo(QiniuUtils.url + "/" + title + multipartFile.getOriginalFilename(), user, title);
        if (!messageInBackground.isSuccess()) {
            return Response.fail("失败");
        }
        else
            return  Response.success("成功");
    }
}
