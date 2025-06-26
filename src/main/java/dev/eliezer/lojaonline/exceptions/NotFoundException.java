package dev.eliezer.lojaonline.exceptions;

public class NotFoundException extends BusinessException{
    public NotFoundException(Long id) {
        super("Resource id " + id + " not found.");
    }
}