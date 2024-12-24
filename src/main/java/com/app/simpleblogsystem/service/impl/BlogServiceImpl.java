package com.app.simpleblogsystem.service.impl;

import com.app.simpleblogsystem.dto.BlogDTO;
import com.app.simpleblogsystem.exception.ResourceNotFoundException;
import com.app.simpleblogsystem.models.Blog;
import com.app.simpleblogsystem.models.Categories;
import org.modelmapper.ModelMapper;
import com.app.simpleblogsystem.repository.*;
import com.app.simpleblogsystem.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public  BlogServiceImpl(BlogRepository blogRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.blogRepository = blogRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Blog> findAllBlog(){
        return blogRepository.findAll();
    }

    @Override
    public Optional<Blog> findBlogById(Long id){
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if (blogOptional.isPresent()){
            return blogOptional;
        }
        return Optional.empty();
    }

    @Override
    public BlogDTO createBlog(BlogDTO blogDTO){
        Blog blog = mapToBlog(blogDTO);
        Blog newBlog = blogRepository.save(blog);
        return mapToDTO(newBlog);
    }

    @Override
    public BlogDTO updateBlog(Long id, BlogDTO blogDTO){
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));

        blog.setTitle(blogDTO.getTitle());
        blog.setDescription(blogDTO.getDescription());
        blog.setImageUrl(blogDTO.getImageUrl());

        Blog updateBlog = blogRepository.save(blog);

        return mapToDTO(updateBlog);
    }

    @Override
    public void deleteBlog(Long id){
        Blog blogId = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));

        blogRepository.delete(blogId);
    }

    @Override
    public List<BlogDTO> getBlogByCategory(Long categoryId){
        Categories category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Blog> blogList = blogRepository.findByCategory(category);

        return blogList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private Blog mapToBlog(BlogDTO blogDTO) {
        return modelMapper.map(blogDTO, Blog.class);
    }

    private BlogDTO mapToDTO(Blog blog){
        return modelMapper.map(blog, BlogDTO.class);
    }

}
