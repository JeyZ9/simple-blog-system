package com.app.simpleblogsystem.dto;

import com.app.simpleblogsystem.dto.user.UserDTO;
import com.app.simpleblogsystem.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class BlogDTO {

    private Long id;

    private String title;

    private String imageUrl;

    private String description;

    private UserDTO users;

    private Category category;

    private String dateTime;

//    private List<CommentDTO> comments;

//    private List<LikeDTO> likes;

//    public String getTitle() {
//        return title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }

//    public BlogDTO(Long id, String title, String description){}
}
