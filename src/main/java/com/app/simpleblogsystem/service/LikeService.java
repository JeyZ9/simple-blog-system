package com.app.simpleblogsystem.service;

import com.app.simpleblogsystem.dto.LikeDTO;

import java.util.List;

public interface LikeService {
    List<LikeDTO> getLikeByBlogId(Long blogId);
    LikeDTO createLike(Long blogId, Long userId, LikeDTO likeDTO);
    LikeDTO updateLike(Long likeId, Long blogId, Long userId, LikeDTO likeDTO);
}
