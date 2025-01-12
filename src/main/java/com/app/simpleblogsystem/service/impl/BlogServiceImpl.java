package com.app.simpleblogsystem.service.impl;

import com.app.simpleblogsystem.dto.BlogDTO;
import com.app.simpleblogsystem.exception.ResourceNotFoundException;
import com.app.simpleblogsystem.models.Blog;
import com.app.simpleblogsystem.models.Category;
import com.app.simpleblogsystem.models.User;
import com.app.simpleblogsystem.repository.*;
import com.app.simpleblogsystem.service.BlogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Value("${upload-dir}")
    private String uploadDir;

    @Autowired
    public  BlogServiceImpl(BlogRepository blogRepository, UserRepository userRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BlogDTO> getAllBlog(){
        List<Blog> blogList = blogRepository.findAll();
        return blogList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BlogDTO> getBlogByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<Blog> blog = blogRepository.findAllByUsersId(user.getId());
        return blog.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BlogDTO> getBlogByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<Blog> blogList = blogRepository.findAllByCategoryId(category.getId());
        return blogList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<Blog> getBlogById(Long id){
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if (blogOptional.isPresent()){
            return blogOptional;
        }
        return Optional.empty();
    }

    @Override
    public Blog createBlog(Long userId, Long categoryId, Blog blog, MultipartFile image) throws IOException {
//        Blog newBlog = new Blog();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        blog.setUsers(user);
        blog.setCategory(category);
        if(image.isEmpty()) {
            blog.setImageUrl("noimg.jpg");
        }else{
            blog.setImageUrl(saveFile(image));
        }
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Long userId, Long categoryId, Blog blog, MultipartFile image) throws IOException {
        Blog findBlog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        findBlog.setTitle(blog.getTitle());
        findBlog.setDescription(blog.getDescription());
        findBlog.setUsers(user);
        findBlog.setCategory(category);
        if(image != null){
            String newFileName = saveFile(image);
            deleteFile(findBlog.getImageUrl());
            findBlog.setImageUrl(newFileName);
        }

        return blogRepository.save(findBlog);
    }

    @Override
    public void deleteBlog(Long id){
        Blog blogId = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));

        blogRepository.delete(blogId);
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        if(!Files.exists(filePath.getParent())){
            Files.createDirectories(filePath.getParent());
        }

        try {
            FileOutputStream fos = new FileOutputStream(filePath.toFile());
            fos.write(file.getBytes());
        }catch (FileNotFoundException e){
            throw new FileNotFoundException(e.getMessage());
        }

        return fileName;
    }

    private void deleteFile(String fileName) throws IOException {
        if(fileName != "noimg.jpg"){
            Path filePath = Paths.get(uploadDir, fileName);
            try{
                Files.deleteIfExists(filePath);
            }catch (IOException e) {
                throw new IOException(e.getMessage());
            }
        }
    }

//    private Blog mapToBlog(BlogDTO blogDTO){
//        return modelMapper.map(blogDTO, Blog.class);
//    }

    private BlogDTO mapToDTO(Blog blog) {
        return modelMapper.map(blog, BlogDTO.class);
    }

}
