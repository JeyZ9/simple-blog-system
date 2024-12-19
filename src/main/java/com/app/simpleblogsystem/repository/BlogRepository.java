package com.app.simpleblogsystem.repository;

import com.app.simpleblogsystem.models.Blog;
import com.app.simpleblogsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Long, Blog> {
    User filndBlogByUserName(String username);
}
