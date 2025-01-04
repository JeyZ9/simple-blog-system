package com.app.simpleblogsystem.service.impl;

import com.app.simpleblogsystem.dto.BlogDTO;
import com.app.simpleblogsystem.exception.ResourceNotFoundException;
import com.app.simpleblogsystem.models.Blog;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final LikeRepository likeRepository;

    @Value("${upload-dir}")
    private String uploadDir;

    @Autowired
    public  BlogServiceImpl(BlogRepository blogRepository, ModelMapper modelMapper, CategoryRepository categoryRepository, LikeRepository likeRepository) {
        this.blogRepository = blogRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.likeRepository = likeRepository;
    }

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
    public Optional<Blog> getBlogById(Long id){
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if (blogOptional.isPresent()){
            return blogOptional;
        }
        return Optional.empty();
    }


//    @Override
//    public Blog createBlog(Blog blog, MultipartFile image) throws IOException {
//        Blog newBlog = new Blog();
//        if(image != null) {
//            newBlog.setImageUrl(saveFile(image));
//        }else{
//            newBlog.setImageUrl("noimg.jpg");
//        }
//        return blogRepository.save(blog);
//    }
    @Override
    public Blog createBlog(Blog blog, MultipartFile image) throws IOException {
//        Blog newBlog = new Blog();
        if(image.isEmpty()) {
            blog.setImageUrl("noimg.jpg");
        }else{
            blog.setImageUrl(saveFile(image));
        }
        return blogRepository.save(blog);
//        Blog addedBlog = blogRepository.save(newBlog);
//        return ;
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

    // ติดแก้ blogRepository join table หา categoryId == categoryId
//    @Override
//    public List<BlogDTO> getBlogByCategory(Long categoryId){
//        Categories category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
//
//        List<Blog> blogList = blogRepository.findBycategories(category.getId());
//
//        return blogList.stream().map(this::mapToDTO).collect(Collectors.toList());
//    }
//
//    private Blog mapToBlog(BlogDTO blogDTO) {
//        return modelMapper.map(blogDTO, Blog.class);
//    }
//
//    private BlogDTO mapToDTO(Blog blog){
//        return modelMapper.map(blog, BlogDTO.class);
//    }

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
