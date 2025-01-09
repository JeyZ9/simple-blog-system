package com.app.simpleblogsystem.dto;

import com.app.simpleblogsystem.dto.user.UserDTO;
import com.app.simpleblogsystem.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String comment;
    private String createdDate;
    private UserDTO user;
//    public String getComment() {
//        return comment;
//    }
}
