package com.pintoo.ems.CustomExceptions;

public class RequestException extends RuntimeException{

    public RequestException(String message) {
        super(message);
    }
}
