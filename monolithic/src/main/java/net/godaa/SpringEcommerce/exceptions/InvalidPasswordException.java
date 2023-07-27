package net.godaa.SpringEcommerce.exceptions;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("Incorrect Password");
    }
}
