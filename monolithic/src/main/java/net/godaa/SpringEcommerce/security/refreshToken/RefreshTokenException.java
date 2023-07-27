package net.godaa.SpringEcommerce.security.refreshToken;

public class RefreshTokenException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RefreshTokenException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}
