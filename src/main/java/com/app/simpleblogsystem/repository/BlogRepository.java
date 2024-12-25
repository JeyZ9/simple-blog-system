package com.app.simpleblogsystem.repository;

import com.app.simpleblogsystem.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
//    User findBlogByUsername(String username);

//    List<Blog> findBycategories(Long categoryId);
//    @Query("SELECT b FROM Blog b WHERE b.categories.id = :categoryId")
//    List<Blog> findBycategories(@Param("categoryId") Long categoryId);
}

