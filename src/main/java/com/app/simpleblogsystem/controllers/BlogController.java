package com.app.simpleblogsystem.controllers;

import com.app.simpleblogsystem.config.ApiResponse;
import com.app.simpleblogsystem.config.MessageStrings;
import com.app.simpleblogsystem.dto.BlogDTO;
import com.app.simpleblogsystem.exception.ResourceNotFoundException;
import com.app.simpleblogsystem.models.Blog;
import com.app.simpleblogsystem.models.Category;
import com.app.simpleblogsystem.models.User;
import com.app.simpleblogsystem.repository.BlogRepository;
import com.app.simpleblogsystem.repository.CategoryRepository;
import com.app.simpleblogsystem.repository.UserRepository;
import com.app.simpleblogsystem.service.BlogService;
import jakarta.validation.Valid;
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
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final BlogRepository blogRepository;

    public BlogController(BlogService blogService, UserRepository userRepository, CategoryRepository categoryRepository, BlogRepository blogRepository) {
        this.blogService = blogService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.blogRepository = blogRepository;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getAllBlog() {
        List<BlogDTO> getBlogs = blogService.getAllBlog();
        return new ResponseEntity<>(new ApiResponse<>(200, MessageStrings.SUCCESS_GET, getBlogs), HttpStatus.OK);
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
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        blog.setUser(user);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        blog.setCategory(category);
        blog.setDateTime(sdf.format(new Date()));

        Blog addedBlog = blogService.createBlog(blog, image);

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
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        blog.setUser(user);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        blog.setCategory(category);
        blog.setDateTime(sdf.format(new Date()));

        Blog addedBlog = blogService.updateBlog(id, blog, image);

        return new ResponseEntity<>(new ApiResponse<>(201, MessageStrings.SUCCESS_CREATE, addedBlog), HttpStatus.CREATED);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ApiResponse<Object>> updateBlog(@PathVariable Long id, @Valid @RequestBody BlogDTO blogDTO){
//        if (Objects.nonNull(blogService.getBlogById(id))){
//            BlogDTO updateBlog = blogService.updateBlog(id, blogDTO);
//            return new ResponseEntity<>(new ApiResponse<>(200, MessageStrings.SUCCESS_UPDATE, updateBlog), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(new ApiResponse<>(404, "Blog does not exists!", null), HttpStatus.NOT_FOUND);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteBlog(@PathVariable Long id){
        if(Objects.nonNull(blogService.getBlogById(id))){
            blogService.deleteBlog(id);
            return new ResponseEntity<>(new ApiResponse<>(201, MessageStrings.SUCCESS_DELETE, null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse<>(404, "Blog not found!", null), HttpStatus.NOT_FOUND);
    }

}
