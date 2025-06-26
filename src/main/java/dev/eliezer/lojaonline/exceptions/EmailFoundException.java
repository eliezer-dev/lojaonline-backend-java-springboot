package dev.eliezer.lojaonline.exceptions;

public class EmailFoundException extends RuntimeException{
    public EmailFoundException(String email) {
        super("Email " + email + " is alright in use.");
    }
}
