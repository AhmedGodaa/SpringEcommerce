package net.godaa.SpringEcommerce.exceptions;

public class AccountNotExistException extends RuntimeException{

    public AccountNotExistException() {
        super("Account Not Exist!");
    }
}
