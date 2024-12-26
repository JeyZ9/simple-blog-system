package com.app.simpleblogsystem.service;

import com.app.simpleblogsystem.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getCommentByBlogId(Long blogId);
    CommentDTO createComment(CommentDTO commentDTO);
    CommentDTO updateComment(Long commentId, Long userId, Long blogId, CommentDTO commentDTO);
    void deleteComment(Long id);
}
