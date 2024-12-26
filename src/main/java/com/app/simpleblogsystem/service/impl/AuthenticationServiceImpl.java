package com.app.simpleblogsystem.service.impl;

import com.app.simpleblogsystem.config.MessageStrings;
import com.app.simpleblogsystem.exception.AuthenticationFailException;
import com.app.simpleblogsystem.models.AuthenticationToken;
import com.app.simpleblogsystem.models.User;
import com.app.simpleblogsystem.repository.TokenRepository;
import com.app.simpleblogsystem.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final TokenRepository tokenRepository;

    @Autowired
    public AuthenticationServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    @Override
    public AuthenticationToken getToken(User user) {
        return tokenRepository.findTokenByUser(user);
    }

    @Override
    public User getUser(String token){
        AuthenticationToken authenticationToken = tokenRepository.findTokenByToken(token);
        if(Objects.nonNull(authenticationToken)){
            if (Objects.nonNull(authenticationToken.getUser())){
                return authenticationToken.getUser();
            }
        }
        return null;
    }

    @Override
    public void authenticate(String token) throws AuthenticationFailException {
        if(!Objects.nonNull(token)){
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }
        if(!Objects.nonNull(getUser(token))){
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_VALID);
        }
    }
}
