package com.example.springserver.exceptions;

public class FoodAlreadyExistsException extends RuntimeException {

    public FoodAlreadyExistsException(String text) {
        super(text);
    }
}
