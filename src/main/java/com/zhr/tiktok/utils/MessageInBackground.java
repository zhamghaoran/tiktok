package com.zhr.tiktok.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageInBackground {
    private boolean success;
    private String message;
    private Map<String ,? > map;
    public static MessageInBackground failed(String message) {
        return new MessageInBackground(false,message,null);
    }
    public static MessageInBackground success(Map<String,?> map) {
        return new MessageInBackground(true,"成功",map);
    }
}
