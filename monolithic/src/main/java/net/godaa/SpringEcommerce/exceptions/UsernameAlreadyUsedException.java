package net.godaa.SpringEcommerce.exceptions;

public class UsernameAlreadyUsedException extends RuntimeException {

    public UsernameAlreadyUsedException() {
        super("Username Already Used!");
    }
}
