package com.app.simpleblogsystem.repository;

import com.app.simpleblogsystem.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAllByBlogId(Long blogId);
    Like findLike(Long likeId, Long blogId, Long userId);
}
