package com.app.simpleblogsystem.service;

import com.app.simpleblogsystem.dto.LikeDTO;

import java.io.IOException;
import java.util.List;

public interface LikeService {
    List<LikeDTO> getLikeByBlogId(Long blogId);
    LikeDTO createLike(Long blogId, Long userId, LikeDTO likeDTO);
//    LikeDTO updateLike(Long likeId, Long blogId, Long userId, LikeDTO likeDTO);
    LikeDTO updateLike(Long blogId, Long likeId, LikeDTO likeDTO, Long userId) throws IOException;
}
