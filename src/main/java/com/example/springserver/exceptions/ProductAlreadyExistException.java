package com.example.springserver.exceptions;

public class ProductAlreadyExistException extends RuntimeException {

    public ProductAlreadyExistException(String text) {
        super(text);
    }
}
