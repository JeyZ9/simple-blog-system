package com.app.simpleblogsystem.repository;

import com.app.simpleblogsystem.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByCategoryId(Long categoryId);
    List<Blog> findAllByUsersId(Long userId);

//    Optional<Object> findBlogById(Set<Long> blogId);
}

