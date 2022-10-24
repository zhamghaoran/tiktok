package com.zhr.tiktok.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.objenesis.instantiator.perc.PercInstantiator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String videoId;
    private String commentId;
    private String commentDetail;
    private String userId;
    private Long data;
}
