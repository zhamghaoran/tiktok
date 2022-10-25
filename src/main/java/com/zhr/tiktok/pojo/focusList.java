package com.zhr.tiktok.pojo;

import com.zhr.tiktok.parmaVo.ReturnUser;
import com.zhr.tiktok.utils.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class focusList extends Response {
    private ReturnUser[] user_list;
    public static @NotNull focusList success(ReturnUser[] user_list) {
        focusList focusList = new focusList(user_list);
        focusList.setStatus_code(0);
        focusList.setStatus_msg("成功");
        return focusList;
    }
    public static focusList fail(String message) {
        focusList focusList = new focusList();
        focusList.setUser_list(null);
        focusList.setStatus_code(-1);
        focusList.setStatus_msg(message);
        return focusList;
    }
}
