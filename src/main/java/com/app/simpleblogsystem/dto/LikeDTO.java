package com.app.simpleblogsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LikeDTO {
    private Long id;
    private String username;
    private Boolean isLiked;

//    public LikeDTO(String username, Boolean isLiked) {
//        this.username = username;
//        this.isLiked = isLiked;
//    }
}
