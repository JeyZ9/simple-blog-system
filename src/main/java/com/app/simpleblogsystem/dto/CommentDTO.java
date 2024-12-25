package com.app.simpleblogsystem.dto;

import com.app.simpleblogsystem.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentDTO {
    private Long id;

    private String comment;

//    private Date date;

    private User user;

    public String getComment() {
        return comment;
    }
}
