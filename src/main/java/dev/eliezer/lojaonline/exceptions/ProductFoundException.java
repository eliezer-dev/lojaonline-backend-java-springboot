package dev.eliezer.lojaonline.exceptions;

public class ProductFoundException extends RuntimeException{
    public ProductFoundException() {
        super("Product alright exists");
    }
}
