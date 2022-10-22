package com.zhr.tiktok.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private Integer status_code;
    private String  status_msg;
    public static Response fail (String message) {
        return new Response(-1,message);
    }
    public static Response success (String message) {
        return new Response(0,message);
    }
}
