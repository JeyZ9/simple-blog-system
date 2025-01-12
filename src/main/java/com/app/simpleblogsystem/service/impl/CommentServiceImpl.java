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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, BlogRepository blogRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CommentDTO> getCommentByBlogId(Long blogId) {
        List<Comment> comments = commentRepository.findByBlogsId(blogId);
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
//    @Override
//    public List<Comment> getCommentByBlogId(Long blogId) {
//        return commentRepository.findByBlogsId(blogId);
//    }

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Long userId, Long blogId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", "Id", blogId));
        Comment comment = mapToComment(commentDTO);
        comment.setComment(commentDTO.getComment());
        comment.setBlogs(blog);
        comment.setUser(user);
        comment.setCreatedDate(sdf.format(new Date()));
        Comment newComment = commentRepository.save(comment);
        return  mapToDTO(newComment);
    }

    @Override
    public CommentDTO updateComment(Long commentId, Long userId, Long blogId, CommentDTO commentDTO) throws IOException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (Objects.equals(comment.getUser().getId(), userId)) {
            comment.setComment(commentDTO.getComment());
            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
            comment.setUser(user);
            Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));
            comment.setBlogs(blog);
            comment.setCreatedDate(sdf.format(new Date()));
            commentRepository.save(comment);
        }else {
            throw new IOException("UserId is not equal");
        }
        return mapToDTO(comment);
    }

    @Override
    public Comment deleteComment(Long blogId, Long commentId) throws IOException {
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        commentRepository.delete(comment);
        if(!comment.getBlogs().getId().equals(blog.getId())){
            throw new IOException("Comment dose not belong to post");
        }
        return comment;
    }

    private CommentDTO mapToDTO(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }

    private Comment mapToComment(CommentDTO commentDTO){
        return  modelMapper.map(commentDTO, Comment.class);
    }

}
