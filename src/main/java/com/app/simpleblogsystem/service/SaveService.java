package com.app.simpleblogsystem.service;

import com.app.simpleblogsystem.dto.save.SaveDTO;
import com.app.simpleblogsystem.dto.save.UserSaveBlogsDTO;

import java.util.List;

public interface SaveService {
    List<SaveDTO> getAllSave();
    List<SaveDTO> getSaveByUser(Long userId);

//    UserSaveBlogsDTO saveBlogs(Long userId, Long blogId, UserSaveBlogsDTO saveBlogsDTO);
    UserSaveBlogsDTO saveBlogs(UserSaveBlogsDTO saveBlogsDTO);
    void deleteBlogFromSave(Long saveId, Long userId, Long blogId);
}
