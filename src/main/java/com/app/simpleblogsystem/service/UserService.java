package com.app.simpleblogsystem.service;

import com.app.simpleblogsystem.dto.user.LoginDTO;
import com.app.simpleblogsystem.dto.user.RegisterDTO;
import com.app.simpleblogsystem.dto.user.UserResponseDTO;
import com.app.simpleblogsystem.exception.AuthenticationFailException;
import com.app.simpleblogsystem.exception.CustomException;

public interface UserService {

    UserResponseDTO login(LoginDTO login) throws AuthenticationFailException, CustomException;
    UserResponseDTO register(RegisterDTO register) throws CustomException;
}
