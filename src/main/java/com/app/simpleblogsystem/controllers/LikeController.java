package com.app.simpleblogsystem.controllers;

import com.app.simpleblogsystem.config.ApiResponse;
import com.app.simpleblogsystem.config.MessageStrings;
import com.app.simpleblogsystem.dto.LikeDTO;
import com.app.simpleblogsystem.models.Like;
import com.app.simpleblogsystem.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/blogs/{blogId}/likes")
    public ResponseEntity<ApiResponse<Object>> getAllCommentsByBlog(@PathVariable("blogId") Long blogId) {
        List<LikeDTO> likeDTOList = likeService.getLikeByBlogId(blogId);
        ApiResponse<Object> response = new ApiResponse<>(200, MessageStrings.SUCCESS_GET, likeDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/blogs/{blogId}/likes")
    public ResponseEntity<ApiResponse<Object>> isLikeBlog(@PathVariable("blogId") Long blogId, @RequestParam boolean isLiked, @RequestParam Long userId) {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setIsLiked(isLiked);
        LikeDTO likeBlog = likeService.createLike(blogId, userId, likeDTO);
        ApiResponse<Object> response = new ApiResponse<>(201, MessageStrings.SUCCESS_CREATE, likeBlog);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/blogs/{blogId}/likes/{likeId}")
    public ResponseEntity<ApiResponse<Object>> updateLike(
            @PathVariable("blogId") Long blogId,
            @RequestParam Boolean isLiked,
            @RequestParam Long userId,
            @PathVariable("likeId") Long likeId
    ) throws IOException {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setIsLiked(isLiked);
        LikeDTO updatedLike = likeService.updateLike(blogId, likeId, likeDTO, userId);
        ApiResponse<Object> response = new ApiResponse<>(201, MessageStrings.SUCCESS_UPDATE, updatedLike);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
