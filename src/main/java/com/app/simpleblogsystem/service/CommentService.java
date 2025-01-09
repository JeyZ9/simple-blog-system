package com.app.simpleblogsystem.service;

import com.app.simpleblogsystem.dto.CommentDTO;
import com.app.simpleblogsystem.models.Comment;

import java.io.IOException;
import java.util.List;

public interface CommentService {
    List<CommentDTO> getCommentByBlogId(Long blogId);
//    List<Comment> getCommentByBlogId(Long blogId);
    CommentDTO createComment(CommentDTO commentDTO, Long userId, Long blogId);
    CommentDTO updateComment(Long commentId, Long userId, Long blogId, CommentDTO commentDTO) throws IOException;
    Comment deleteComment(Long blogId, Long commentId) throws IOException;
}
