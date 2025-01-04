package com.app.simpleblogsystem.service.impl;

import com.app.simpleblogsystem.config.MessageStrings;
import com.app.simpleblogsystem.dto.user.LoginDTO;
import com.app.simpleblogsystem.dto.user.RegisterDTO;
import com.app.simpleblogsystem.dto.user.UserResponseDTO;
import com.app.simpleblogsystem.exception.AuthenticationFailException;
import com.app.simpleblogsystem.exception.CustomException;
import com.app.simpleblogsystem.models.AuthenticationToken;
import com.app.simpleblogsystem.models.Role;
import com.app.simpleblogsystem.models.RoleName;
import com.app.simpleblogsystem.models.User;
import com.app.simpleblogsystem.repository.RoleRepository;
import com.app.simpleblogsystem.repository.UserRepository;
import com.app.simpleblogsystem.service.AuthenticationService;
import com.app.simpleblogsystem.service.UserService;
import jakarta.xml.bind.DatatypeConverter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationService authenticationService, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserResponseDTO register(RegisterDTO registerDTO) throws CustomException {
        if(Objects.nonNull(userRepository.findByEmail(registerDTO.getEmail()))){
            throw new CustomException("User already exists!");
        }

        String encryptedPassword = registerDTO.getPassword();
        try {
            encryptedPassword = hashPassword(registerDTO.getPassword());
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }

        Role role = roleRepository.findRoleByRoleName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found!"));

//        User user = new User(registerDTO.getUsername(), registerDTO.getEmail(), encryptedPassword);
        User user = mapToUser(registerDTO);
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(encryptedPassword);
        user.getRoles().add(role);
        try {
            userRepository.save(user);
            final AuthenticationToken authToken = new AuthenticationToken(user);
            authenticationService.saveConfirmationToken(authToken);
            return new UserResponseDTO("Success", "User created successfully");
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public UserResponseDTO login(LoginDTO loginDTO) throws AuthenticationFailException, CustomException {
        User user = userRepository.findByEmail(loginDTO.getEmail());
        if(!Objects.nonNull(user)){
            throw new AuthenticationFailException("User not present!");
        }
        try{
            if(!user.getPassword().equals(hashPassword(loginDTO.getPassword()))){
                throw new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            logger.error("hashing password fails {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if(!Objects.nonNull(token)){
            throw new CustomException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }

        return new UserResponseDTO("success", token.getToken());
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();

    }

    public User mapToUser(RegisterDTO register) {
        return modelMapper.map(register, User.class);
    }

}
