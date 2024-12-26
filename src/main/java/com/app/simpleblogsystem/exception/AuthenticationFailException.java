package com.app.simpleblogsystem.exception;

public class AuthenticationFailException extends Exception{
    public AuthenticationFailException(String msg){
        super(msg);
    }
}
