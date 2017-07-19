package com.github.aarexer.crud.dao;

public class DaoInitializationException extends RuntimeException {
    public DaoInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoInitializationException(Throwable cause) {
        super(cause);
    }
}
