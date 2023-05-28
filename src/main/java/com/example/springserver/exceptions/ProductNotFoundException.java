package com.example.springserver.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String text) {
        super(text);
    }
}
