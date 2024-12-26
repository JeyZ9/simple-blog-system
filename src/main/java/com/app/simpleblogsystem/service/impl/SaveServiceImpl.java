package com.app.simpleblogsystem.service.impl;

import com.app.simpleblogsystem.dto.save.SaveDTO;
import com.app.simpleblogsystem.dto.save.UserSaveBlogsDTO;
import com.app.simpleblogsystem.exception.ResourceNotFoundException;
import com.app.simpleblogsystem.models.Blog;
import com.app.simpleblogsystem.models.Save;
import com.app.simpleblogsystem.models.User;
import com.app.simpleblogsystem.repository.BlogRepository;
import com.app.simpleblogsystem.repository.SaveRepository;
import com.app.simpleblogsystem.repository.UserRepository;
import com.app.simpleblogsystem.service.SaveService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaveServiceImpl implements SaveService {

    private final SaveRepository saveRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SaveServiceImpl(SaveRepository saveRepository, UserRepository userRepository, BlogRepository blogRepository, ModelMapper modelMapper) {
        this.saveRepository = saveRepository;
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaveDTO> getAllSave(){
        List<Save> saveList = saveRepository.findAll();
        return saveList.stream().map(this::mapToSaveDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaveDTO> getSaveByUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<Save> saveList = saveRepository.findSaveByUserId(user.getId());
        return saveList.stream().map(this::mapToSaveDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
//    public UserSaveBlogsDTO saveBlogs(Long userId,  Long blogId, UserSaveBlogsDTO userSaveBlogsDTO){
    public UserSaveBlogsDTO saveBlogs(UserSaveBlogsDTO userSaveBlogsDTO){
        User user = userRepository.findById(userSaveBlogsDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userSaveBlogsDTO.getUserId()));
        Blog blog = blogRepository.findById(userSaveBlogsDTO.getBlogId()).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", userSaveBlogsDTO.getBlogId()));
        Save save = mapToSave(userSaveBlogsDTO);
        save.setUsers(user);
        save.setBlogs(blog);
        Save newSave = saveRepository.save(save);
        return mapToUserSaveDTO(newSave);
    }

    @Override
    public void deleteBlogFromSave(Long saveId, Long userId, Long blogId){
        Save save = saveRepository.findBySaveIdAndUserIdAndBlogId(saveId, userId, blogId).orElseThrow(() -> new ResourceNotFoundException("Save", "id", saveId));
        saveRepository.delete(save);
    }

    private Save mapToSave(UserSaveBlogsDTO userSaveBlogsDTO){
        return modelMapper.map(userSaveBlogsDTO, Save.class);
    }

    private SaveDTO mapToSaveDTO(Save save){
        return modelMapper.map(save, SaveDTO.class);
    }

    private UserSaveBlogsDTO mapToUserSaveDTO(Save save){
        return modelMapper.map(save, UserSaveBlogsDTO.class);
    }


}
