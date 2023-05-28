package com.example.springserver.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String text) {
        super(text);
    }
}
