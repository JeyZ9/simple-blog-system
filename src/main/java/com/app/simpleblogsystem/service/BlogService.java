package com.app.simpleblogsystem.service;

import com.app.simpleblogsystem.models.Blog;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BlogService {
    List<Blog> findAllBlog();

    Optional<Blog> findBlogById(Long id);

    Blog createBlog();

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
}
