package com.leonardoramos.shortme.exception;

public class ShortUrlNotFoundException extends RuntimeException {
    public ShortUrlNotFoundException(String message) {
        super(message);
    }

    public ShortUrlNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
