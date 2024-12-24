package com.app.simpleblogsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BlogDTO {

    private Long id;

    private String title;

    private String imageUrl;

    private String description;

    private Long userId;

    private Long categoryId;

    private Set<CommentDTO> comments;

    private Set<LikeDTO> likes;
}
