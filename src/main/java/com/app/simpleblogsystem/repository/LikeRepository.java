package com.app.simpleblogsystem.repository;

import com.app.simpleblogsystem.models.Blog;
import com.app.simpleblogsystem.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
//    List<Like> findAllByBlogs(Long blogId);
    List<Like> findAllByBlogs(Blog blogs);
    Optional<Like> findByBlogs(Blog blogs);
}
