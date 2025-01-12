package com.app.simpleblogsystem.controllers;

import com.app.simpleblogsystem.dto.JwtAuthResponse;
import com.app.simpleblogsystem.dto.user.LoginDTO;
import com.app.simpleblogsystem.dto.user.RegisterDTO;
import com.app.simpleblogsystem.dto.user.UserResponseDTO;
import com.app.simpleblogsystem.exception.AuthenticationFailException;
import com.app.simpleblogsystem.exception.CustomException;
import com.app.simpleblogsystem.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users")
//@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService =  userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDTO loginDTO){
        String token = userService.login(loginDTO);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setAccessToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) throws CustomException {
        String user = userService.register(registerDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
