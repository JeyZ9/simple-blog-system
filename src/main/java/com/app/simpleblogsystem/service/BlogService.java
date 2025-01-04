package com.app.simpleblogsystem.service;

import com.app.simpleblogsystem.dto.BlogDTO;
import com.app.simpleblogsystem.models.Blog;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BlogService {
    List<BlogDTO> getAllBlog();

    Optional<Blog> getBlogById(Long id);

//    BlogDTO createBlog(BlogDTO blogDTO);

    Blog createBlog(Blog blog, MultipartFile image) throws IOException;

    Blog updateBlog(Long id, Blog blog, MultipartFile image) throws IOException;

    void deleteBlog(Long id);

//    List<BlogDTO> getBlogByCategory(Long categoryId);
}
