package com.example.springserver.exceptions;

public class StatsNotFoundException extends RuntimeException {

    public StatsNotFoundException(String text) {
        super(text);
    }
}
