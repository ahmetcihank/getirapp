package org.getir.exception;

public class BookNotFindException extends RuntimeException {
    public BookNotFindException(String message) {
        super(message);
    }
}
