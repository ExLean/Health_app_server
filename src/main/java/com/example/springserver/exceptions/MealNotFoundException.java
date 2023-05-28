package com.example.springserver.exceptions;

public class MealNotFoundException extends RuntimeException {

    public MealNotFoundException(String text) {
        super(text);
    }
}
