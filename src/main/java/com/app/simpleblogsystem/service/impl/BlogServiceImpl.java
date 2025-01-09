package com.app.simpleblogsystem.service.impl;

import com.app.simpleblogsystem.dto.BlogDTO;
import com.app.simpleblogsystem.exception.ResourceNotFoundException;
import com.app.simpleblogsystem.models.Blog;
//import org.modelmapper.ModelMapper;
import com.app.simpleblogsystem.models.Category;
import com.app.simpleblogsystem.models.User;
import com.app.simpleblogsystem.repository.*;
import com.app.simpleblogsystem.service.BlogService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
//    private final ModelMapper modelMapper;
//    private final CategoryRepository categoryRepository;
//    private final LikeRepository likeRepository;

    @Value("${upload-dir}")
    private String uploadDir;

    @Autowired
    public  BlogServiceImpl(BlogRepository blogRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }
//    public  BlogServiceImpl(BlogRepository blogRepository, ModelMapper modelMapper, CategoryRepository categoryRepository, LikeRepository likeRepository) {
//        this.blogRepository = blogRepository;
//        this.modelMapper = modelMapper;
//        this.categoryRepository = categoryRepository;
//        this.likeRepository = likeRepository;
//    }

    @Override
    public List<BlogDTO> getAllBlog(){
        List<Blog> blogList = blogRepository.findAll();
        List<BlogDTO> blogDTOs = new ArrayList<>();
        for(Blog blog : blogList){
            BlogDTO blogDTO = new BlogDTO();
//            List<Like> like = likeRepository.findAllByBlogs(blog);
//            List<LikeDTO> likeDTO = like.stream().map(i -> new LikeDTO(i.getId(), i.getUsers().getUsername(), i.getIsLiked())).collect(Collectors.toList());
            blogDTO.setId(blog.getId());
            blogDTO.setWriter(blog.getUser().getUsername());
            blogDTO.setTitle(blog.getTitle());
            blogDTO.setCategory(blog.getCategory());
            blogDTO.setDescription(blog.getDescription());
            blogDTO.setCreatedDate(blog.getDateTime());
//            blogDTO.setLikes(likeDTO);
            blogDTO.setImageUrl(blog.getImageUrl());
            blogDTOs.add(blogDTO);
        }
        return blogDTOs;
    }

    @Override
    public List<BlogDTO> getBlogByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<Blog> blogList = blogRepository.findAllByUserId(user.getId());
        List<BlogDTO> blogDTOs = new ArrayList<>();
        for(Blog blog : blogList){
            BlogDTO blogDTO = new BlogDTO();
            blogDTO.setId(blog.getId());
            blogDTO.setWriter(blog.getUser().getUsername());
            blogDTO.setTitle(blog.getTitle());
            blogDTO.setCategory(blog.getCategory());
            blogDTO.setDescription(blog.getDescription());
            blogDTO.setCreatedDate(blog.getDateTime());
            blogDTO.setImageUrl(blog.getImageUrl());
            blogDTOs.add(blogDTO);
        }
        return blogDTOs;
    }

    @Override
    public List<BlogDTO> getBlogByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));
        List<Blog> blogList = blogRepository.findAllByCategoryId(category.getId());
        List<BlogDTO> blogDTOs = new ArrayList<>();
        for(Blog blog : blogList){
            BlogDTO blogDTO = new BlogDTO();
            blogDTO.setId(blog.getId());
            blogDTO.setWriter(blog.getUser().getUsername());
            blogDTO.setTitle(blog.getTitle());
            blogDTO.setCategory(blog.getCategory());
            blogDTO.setDescription(blog.getDescription());
            blogDTO.setCreatedDate(blog.getDateTime());
            blogDTO.setImageUrl(blog.getImageUrl());
            blogDTOs.add(blogDTO);
        }
        return blogDTOs;
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
    public Blog createBlog(Blog blog, MultipartFile image) throws IOException {
//        Blog newBlog = new Blog();
        if(image.isEmpty()) {
            blog.setImageUrl("noimg.jpg");
        }else{
            blog.setImageUrl(saveFile(image));
        }
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog, MultipartFile image) throws IOException {
        Blog findBlog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));

        findBlog.setTitle(blog.getTitle());
        findBlog.setDescription(blog.getDescription());
        if(image != null){
            String newFileName = saveFile(image);
            deleteFile(findBlog.getImageUrl());
            findBlog.setImageUrl(newFileName);
        }

        Blog updateBlog = blogRepository.save(findBlog);

        return updateBlog;
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

}
