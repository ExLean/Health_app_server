package com.example.springserver.exceptions;

public class FoodNotFoundException extends RuntimeException {

    public FoodNotFoundException(String text) {
        super(text);
    }
}
