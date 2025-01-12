package com.app.simpleblogsystem.service.impl;

import com.app.simpleblogsystem.dto.user.LoginDTO;
import com.app.simpleblogsystem.dto.user.RegisterDTO;
import com.app.simpleblogsystem.dto.user.UserResponseDTO;
import com.app.simpleblogsystem.exception.AppException;
import com.app.simpleblogsystem.exception.CustomException;
import com.app.simpleblogsystem.exception.ResourceNotFoundException;
import com.app.simpleblogsystem.models.AuthenticationToken;
import com.app.simpleblogsystem.models.Role;
import com.app.simpleblogsystem.models.RoleName;
import com.app.simpleblogsystem.models.User;
import com.app.simpleblogsystem.repository.RoleRepository;
import com.app.simpleblogsystem.repository.UserRepository;
import com.app.simpleblogsystem.security.JwtTokenProvider;
import com.app.simpleblogsystem.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

//    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public String register(RegisterDTO register){
        if(userRepository.existsByUsername(register.getUsername())){
            throw new AppException(HttpStatus.BAD_REQUEST, "Username already exist!.");
        }

        User user = mapToUser(register);
        user.setUsername(register.getUsername());
        user.setEmail(register.getEmail());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findRoleByRoleName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found!"));
        Role adminRole = roleRepository.findRoleByRoleName(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Role not found!"));
        if(register.getIsAdmin()){
            roles.add(adminRole);
        }else{
            roles.add(userRole);
        }

        user.setRoles(roles);
        userRepository.save(user);

        return "User registered successfully!";

    }

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    public User mapToUser(RegisterDTO register) {
        return modelMapper.map(register, User.class);
    }

}
