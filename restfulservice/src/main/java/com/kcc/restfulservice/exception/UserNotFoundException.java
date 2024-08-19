package com.kcc.restfulservice.exception;

//2xx -> ok
//3xx -> redirect
//4xx -> client error
//500 -> server error

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// HttpStatus.NOT_FOUND -> 404를 나타냄.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    
    // 나만의 Exception을 만듦
    public UserNotFoundException(String message) {
        super(message);
    }
}
