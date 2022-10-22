package com.zhr.tiktok.pojo;

import com.zhr.tiktok.parmaVo.ReturnUser;
import com.zhr.tiktok.utils.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class UserReturn extends Response {
    private Map<Integer, ReturnUser> user;
    public UserReturn(Map<Integer,ReturnUser> map, Integer code,String message) {
        super(code,message);
        this.user = map;
    }
}
