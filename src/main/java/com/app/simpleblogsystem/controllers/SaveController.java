package com.app.simpleblogsystem.controllers;

import com.app.simpleblogsystem.config.ApiResponse;
import com.app.simpleblogsystem.config.MessageStrings;
import com.app.simpleblogsystem.dto.save.SaveDTO;
import com.app.simpleblogsystem.dto.save.UserSaveBlogsDTO;
import com.app.simpleblogsystem.service.SaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SaveController {
    private final SaveService saveService;

    @Autowired
    public SaveController(SaveService saveService) {
        this.saveService = saveService;
    }

    @GetMapping("/user/{userId}/save")
    public ResponseEntity<ApiResponse<Object>> getSaveByUser(@PathVariable Long userId){
        List<SaveDTO> saveDTO = saveService.getSaveByUser(userId);
        ApiResponse<Object> response = new ApiResponse<> (200, MessageStrings.SUCCESS_GET, saveDTO);
        return new ResponseEntity<> (response, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/save")
    public ResponseEntity<ApiResponse<Object>> saveBlog(
            @PathVariable("userId") Long userId,
            @RequestBody UserSaveBlogsDTO userSaveBlog
            ){
        userSaveBlog.setUserId(userId);
        UserSaveBlogsDTO saveDTO = saveService.saveBlogs(userSaveBlog);
        ApiResponse<Object> response = new ApiResponse<> (201, MessageStrings.SUCCESS_CREATE, saveDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{userId}/save")
    public ResponseEntity<ApiResponse<Object>> removeBlogFromSave(
            @PathVariable("userId") Long userId,
            @RequestBody UserSaveBlogsDTO userSaveBlog
    ){
        userSaveBlog.setUserId(userId);
        UserSaveBlogsDTO saveDTO = saveService.deleteBlogFromSave(userSaveBlog);
        ApiResponse<Object> response = new ApiResponse<> (204, MessageStrings.SUCCESS_DELETE, saveDTO);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
