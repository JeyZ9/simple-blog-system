package com.app.simpleblogsystem.service.impl;

import com.app.simpleblogsystem.models.Blog;
import com.app.simpleblogsystem.repository.BlogRepository;
import com.app.simpleblogsystem.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public  BlogServiceImpl(BlogRepository blogRepository){
        this.blogRepository = blogRepository;
    }

    @Override
    public List<Blog> findAllBlog(){
        return blogRepository.findAll();
    }

}
