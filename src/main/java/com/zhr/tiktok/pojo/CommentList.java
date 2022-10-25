package com.zhr.tiktok.pojo;

import com.zhr.tiktok.utils.Response;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.awt.peer.CanvasPeer;

@Data
public class CommentList extends Response {
    private comment_list[] comment_list;
}
