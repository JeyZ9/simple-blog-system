package com.app.simpleblogsystem.controllers;

import com.app.simpleblogsystem.dto.user.LoginDTO;
import com.app.simpleblogsystem.dto.user.RegisterDTO;
import com.app.simpleblogsystem.dto.user.UserResponseDTO;
import com.app.simpleblogsystem.exception.AuthenticationFailException;
import com.app.simpleblogsystem.exception.CustomException;
import com.app.simpleblogsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService =  userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginDTO loginDTO) throws CustomException, AuthenticationFailException {
        UserResponseDTO user = userService.login(loginDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterDTO registerDTO) throws CustomException {
        UserResponseDTO user = userService.register(registerDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
