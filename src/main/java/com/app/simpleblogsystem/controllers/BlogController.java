package com.app.simpleblogsystem.controllers;

import com.app.simpleblogsystem.config.ApiResponse;
import com.app.simpleblogsystem.config.MessageStrings;
import com.app.simpleblogsystem.dto.BlogDTO;
import com.app.simpleblogsystem.models.Blog;
import com.app.simpleblogsystem.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {
    private final BlogService blogService;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getAllBlog() {
        List<BlogDTO> getBlogs = blogService.getAllBlog();
        return new ResponseEntity<>(new ApiResponse<>(200, MessageStrings.SUCCESS_GET, getBlogs), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<Object>> getAllBlogByUserId(@PathVariable("userId") Long userId){
        List<BlogDTO> getBlogs = blogService.getBlogByUserId(userId);
        ApiResponse<Object> response = new ApiResponse<>(200, MessageStrings.SUCCESS_GET, getBlogs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<Object>> getAllBlogByCategoryId(@PathVariable("categoryId") Long categoryId){
        List<BlogDTO> getBlogs = blogService.getBlogByCategoryId(categoryId);
        ApiResponse<Object> response = new ApiResponse<>(200, MessageStrings.SUCCESS_GET, getBlogs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getBlogById(@PathVariable Long id) {
        Blog blogOptional = blogService.getBlogById(id).orElseThrow(() -> new RuntimeException("Blog id not found!"));
        ApiResponse<Object> apiResponse = new ApiResponse<>(200, MessageStrings.SUCCESS_GET, blogOptional);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> addBlog(
            @RequestParam String title,
            @RequestParam MultipartFile image,
            @RequestParam String description,
            @RequestParam Long userId,
            @RequestParam Long categoryId
    ) throws IOException {
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setImageUrl(String.valueOf(image));
        blog.setDescription(description);
        blog.setDateTime(sdf.format(new Date()));

        Blog addedBlog = blogService.createBlog(userId, categoryId, blog, image);

        return new ResponseEntity<>(new ApiResponse<>(201, MessageStrings.SUCCESS_CREATE, addedBlog), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateBlog(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam MultipartFile image,
            @RequestParam String description,
            @RequestParam Long userId,
            @RequestParam Long categoryId
    ) throws IOException {
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setImageUrl(String.valueOf(image));
        blog.setDescription(description);
        blog.setDateTime(sdf.format(new Date()));

        Blog addedBlog = blogService.updateBlog(id, userId, categoryId, blog, image);

        return new ResponseEntity<>(new ApiResponse<>(201, MessageStrings.SUCCESS_CREATE, addedBlog), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteBlog(@PathVariable Long id){
        if(Objects.nonNull(blogService.getBlogById(id))){
            blogService.deleteBlog(id);
            return new ResponseEntity<>(new ApiResponse<>(204, MessageStrings.SUCCESS_DELETE, null), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new ApiResponse<>(404, "Blog not found!", null), HttpStatus.NOT_FOUND);
    }

}
