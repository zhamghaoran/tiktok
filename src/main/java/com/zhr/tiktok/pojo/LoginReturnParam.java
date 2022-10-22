package com.zhr.tiktok.pojo;

import com.zhr.tiktok.utils.Response;
import lombok.Data;

@Data
public class LoginReturnParam extends Response {
    private Integer user_id;
    private String token;
}
