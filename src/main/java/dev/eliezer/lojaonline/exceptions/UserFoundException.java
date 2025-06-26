package dev.eliezer.lojaonline.exceptions;

public class UserFoundException extends RuntimeException{
    public UserFoundException() {
        super("User alright exists");
    }
}
