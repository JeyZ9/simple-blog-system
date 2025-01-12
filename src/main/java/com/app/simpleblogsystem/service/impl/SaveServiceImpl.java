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

import java.util.HashSet;
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

//    @Override
//    @Transactional(readOnly = true)
//    public List<SaveDTO> getAllSave(){
//        List<Save> saveList = saveRepository.findAll();
//        return saveList.stream().map(this::mapToSaveDTO).collect(Collectors.toList());
//    }

    @Override
    @Transactional(readOnly = true)
    public List<SaveDTO> getSaveByUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<Save> saveList = saveRepository.findSaveByUsersId(user.getId());
        return saveList.stream().map(this::mapToSaveDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserSaveBlogsDTO saveBlogs(UserSaveBlogsDTO userSaveBlogsDTO) {
        User user = userRepository.findById(userSaveBlogsDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userSaveBlogsDTO.getUserId()));
        Blog blog = blogRepository.findById(userSaveBlogsDTO.getBlogId().iterator().next())
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", userSaveBlogsDTO.getBlogId().iterator().next()));

        // ค้นหา Save Entity ที่เกี่ยวข้องกับ User
        Save save = saveRepository.findByUsers(user).orElse(new Save(user));

        // ตรวจสอบและเพิ่ม Blog หากยังไม่มีในเซต
        if (save.getBlogs() == null) {
            save.setBlogs(new HashSet<>());
        }
        if (!save.getBlogs().contains(blog)) {
            save.getBlogs().add(blog);
        }
        Save newSave = saveRepository.save(save);
        return mapToUserSaveDTO(newSave);
    }

//    @Override
//    public void deleteBlogFromSave(Long saveId, Long userId, Long blogId){
//        Save save = saveRepository.findBySaveIdAndUserIdAndBlogId(saveId, userId, blogId).orElseThrow(() -> new ResourceNotFoundException("Save", "id", saveId));
//        saveRepository.delete(save);
//    }

    @Override
    public UserSaveBlogsDTO deleteBlogFromSave(UserSaveBlogsDTO userSave){
        User user = userRepository.findById(userSave.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userSave.getUserId()));
        Save save = saveRepository.findByUsers(user).orElseThrow(() -> new ResourceNotFoundException("Save", "id", user.getSave().iterator().next().getId()));
        Blog blog = blogRepository.findById(userSave.getBlogId().iterator().next()).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", userSave.getBlogId().iterator().next()));

        if(save.getBlogs() != null && save.getBlogs().contains(blog)){
            save.getBlogs().remove(blog);
            saveRepository.save(save);
        }else{
            throw new ResourceNotFoundException("Blog", "id", blog.getId());
        }

        return mapToUserSaveDTO(save);
    }

//    private Save mapToSave(UserSaveBlogsDTO userSaveBlogsDTO){
//        return modelMapper.map(userSaveBlogsDTO, Save.class);
//    }

    private SaveDTO mapToSaveDTO(Save save){
        return modelMapper.map(save, SaveDTO.class);
    }

//    private UserSaveBlogsDTO mapToUserSaveDTO(Save save){
//        return modelMapper.map(save, UserSaveBlogsDTO.class);
//    }

    private UserSaveBlogsDTO mapToUserSaveDTO(Save save) {
        UserSaveBlogsDTO dto = new UserSaveBlogsDTO();
        dto.setUserId(save.getUsers().getId());
        dto.setBlogId(save.getBlogs().stream().map(Blog::getId).collect(Collectors.toSet()));
        return dto;
    }


}
