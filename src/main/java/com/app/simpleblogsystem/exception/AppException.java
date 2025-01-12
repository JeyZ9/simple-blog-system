package com.app.simpleblogsystem.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException{

    private final HttpStatus status;

    private final String message;

    public AppException(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

    public AppException(String message, HttpStatus status, String message1){
        super(message);
        this.status = status;
        this.message = message1;
    }

//    public HttpStatus getStatus(){
//        return status;
//    }

    @Override
    public String getMessage(){
        return message;
    }
}
