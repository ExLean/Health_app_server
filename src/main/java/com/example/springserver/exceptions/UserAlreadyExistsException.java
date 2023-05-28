package com.example.springserver.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String text) {
        super(text);
    }
}
