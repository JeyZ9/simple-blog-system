package com.app.simpleblogsystem.dto;

import com.app.simpleblogsystem.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
    private Long id;
//    private String username;
    private Boolean isLiked;
//    private BlogDTO blog;
    private UserDTO users;
//    public LikeDTO(String username, Boolean isLiked) {
//        this.username = username;
//        this.isLiked = isLiked;
//    }
}
