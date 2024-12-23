package com.app.simpleblogsystem.repository;

import com.app.simpleblogsystem.models.Blog;
import com.app.simpleblogsystem.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
//    User findBlogByUsername(String username);

    List<Blog> findByCategory(Categories categories);
}
