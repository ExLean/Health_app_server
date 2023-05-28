package com.example.springserver.exceptions;

public class HistoryNotFoundException extends RuntimeException {

    public HistoryNotFoundException(String text) {
        super(text);
    }
}
