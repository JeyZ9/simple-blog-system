package com.app.simpleblogsystem.service;

import com.app.simpleblogsystem.exception.AuthenticationFailException;
import com.app.simpleblogsystem.models.AuthenticationToken;
import com.app.simpleblogsystem.models.User;

public interface AuthenticationService {
    void saveConfirmationToken(AuthenticationToken authenticationToken);
    AuthenticationToken getToken(User user);
    User getUser(String token);
    void authenticate(String token) throws AuthenticationFailException;
}
