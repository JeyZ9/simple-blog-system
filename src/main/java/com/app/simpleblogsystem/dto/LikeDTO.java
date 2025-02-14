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
    private Boolean isLiked;
    private UserDTO users;
}
