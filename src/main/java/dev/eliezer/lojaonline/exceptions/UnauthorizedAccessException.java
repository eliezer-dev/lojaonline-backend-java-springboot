package dev.eliezer.lojaonline.exceptions;

public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException() {

        super("Operation not permitted.");
    }
}
