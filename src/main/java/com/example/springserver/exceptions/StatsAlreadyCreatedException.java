package com.example.springserver.exceptions;

public class StatsAlreadyCreatedException extends RuntimeException {

    public StatsAlreadyCreatedException(String text) {
        super(text);
    }
}
