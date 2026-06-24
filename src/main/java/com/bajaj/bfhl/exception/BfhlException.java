package com.bajaj.bfhl.exception;

public class BfhlException extends RuntimeException {

    public BfhlException(String message) {
        super(message);
    }

    public BfhlException(String message, Throwable cause) {
        super(message, cause);
    }
}
