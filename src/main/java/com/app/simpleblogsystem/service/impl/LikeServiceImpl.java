package com.app.simpleblogsystem.service.impl;

import com.app.simpleblogsystem.dto.LikeDTO;
import com.app.simpleblogsystem.exception.ResourceNotFoundException;
import com.app.simpleblogsystem.models.Blog;
import com.app.simpleblogsystem.models.Like;
import com.app.simpleblogsystem.models.User;
import com.app.simpleblogsystem.repository.BlogRepository;
import com.app.simpleblogsystem.repository.LikeRepository;
import com.app.simpleblogsystem.repository.UserRepository;
import com.app.simpleblogsystem.service.LikeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, BlogRepository blogRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.likeRepository = likeRepository;
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LikeDTO> getLikeByBlogId(Long blogId) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));
        List<Like> likeList = likeRepository.findAllByBlogs(blog);
        return likeList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LikeDTO createLike(Long blogId, Long userId, LikeDTO likeDTO) {
        Like like = mapToLike(likeDTO);
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));
        like.setBlogs(blog);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        like.setUsers(user);
        like.setIsLiked(likeDTO.getIsLiked());
        Like newLike = likeRepository.save(like);
        return mapToDTO(newLike);
    }

//    @Override
//    @Transactional
//    public LikeDTO updateLike(Long likeId, Long blogId, Long userId, LikeDTO likeDTO){
//        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));
////        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
////        Like like = likeRepository.findLike(likeId, blog.getId(), user.getId());
//        Like like = likeRepository.findLike(blog.getId());
//        if(Objects.isNull(like)){
//            throw new ResourceNotFoundException("Like", "id", likeId);
//        }
//        like.setIsLiked(likeDTO.getIsLiked());
//        Like updatedLike = likeRepository.save(like);
//        return mapToDTO(updatedLike);
//    }

    @Override
    @Transactional
    public LikeDTO updateLike(Long blogId, LikeDTO likeDTO) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));
        Like like = likeRepository.findByBlogs(blog)
                .orElseThrow(() -> new ResourceNotFoundException("Like", "blogId", blogId));
        like.setIsLiked(likeDTO.getIsLiked());
        Like updatedLike = likeRepository.save(like);
        return mapToDTO(updatedLike);
    }

    public LikeDTO mapToDTO(Like like) {
        return modelMapper.map(like, LikeDTO.class);
    }

    public Like mapToLike(LikeDTO likeDTO) {
        return modelMapper.map(likeDTO, Like.class);
    }
}
