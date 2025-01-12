package com.app.simpleblogsystem.dto;

import com.app.simpleblogsystem.dto.user.UserDTO;
import com.app.simpleblogsystem.models.Category;
import lombok.Getter;
import lombok.Setter;

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
}
