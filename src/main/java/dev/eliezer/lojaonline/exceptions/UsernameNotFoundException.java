package dev.eliezer.lojaonline.exceptions;

public class UsernameNotFoundException extends RuntimeException {

    public UsernameNotFoundException() {

        super("e-mail or password incorrect");
    }
}
