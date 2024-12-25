package com.app.simpleblogsystem.service;

import com.app.simpleblogsystem.dto.BlogDTO;
import com.app.simpleblogsystem.models.Blog;

import java.util.List;
import java.util.Optional;

public interface BlogService {
    List<Blog> findAllBlog();

    Optional<Blog> findBlogById(Long id);

    BlogDTO createBlog(BlogDTO blogDTO);

    BlogDTO updateBlog(Long id, BlogDTO blogDTO);

    void deleteBlog(Long id);

//    List<BlogDTO> getBlogByCategory(Long categoryId);
}
