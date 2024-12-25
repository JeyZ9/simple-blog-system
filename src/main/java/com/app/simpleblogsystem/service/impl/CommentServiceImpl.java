package com.app.simpleblogsystem.service.impl;

import com.app.simpleblogsystem.dto.CommentDTO;
import com.app.simpleblogsystem.exception.ResourceNotFoundException;
import com.app.simpleblogsystem.models.Blog;
import com.app.simpleblogsystem.models.Comment;
import com.app.simpleblogsystem.models.User;
import com.app.simpleblogsystem.repository.BlogRepository;
import com.app.simpleblogsystem.repository.CommentRepository;
import com.app.simpleblogsystem.repository.UserRepository;
import com.app.simpleblogsystem.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, BlogRepository blogRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CommentDTO> findByBlogId(Long blogId) {
        List<Comment> comments = commentRepository.findByBlogsId(blogId);
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDTO createComment(CommentDTO commentDTO) {
        Comment comment = mapToComment(commentDTO);
        Comment newComment = commentRepository.save(comment);
        return  mapToDTO(newComment);
    }

    @Override
    public CommentDTO updateComment(Long commentId, Long userId, Long blogId, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        comment.setComment(commentDTO.getComment());
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        comment.setUser(user);
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));
        comment.setBlogs(blog);
        commentRepository.save(comment);
        return mapToDTO(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        commentRepository.delete(comment);
    }

    private CommentDTO mapToDTO(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }

    private Comment mapToComment(CommentDTO commentDTO){
        return  modelMapper.map(commentDTO, Comment.class);
    }
}
