package dev.eliezer.lojaonline.exceptions;

public class ItemFoundException extends RuntimeException{
    public ItemFoundException(String itemDescription) {
        super(itemDescription + " is alright in use.");
    }
}
