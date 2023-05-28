package com.example.springserver.exceptions;

public class HistoryAlreadyCreatedException extends RuntimeException {

    public HistoryAlreadyCreatedException(String text) {
        super(text);
    }
}
