package net.godaa.SpringEcommerce.exceptions;

public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException() {
        super("Email Already In Use!");
    }
}
