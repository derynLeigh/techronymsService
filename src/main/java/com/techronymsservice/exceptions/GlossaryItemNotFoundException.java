package com.techronymsservice.exceptions;

public class GlossaryItemNotFoundException extends RuntimeException {
    public GlossaryItemNotFoundException(String message) {
        super(message);
    }
}
