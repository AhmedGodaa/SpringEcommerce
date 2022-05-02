package net.godaa.SpringEcommerce.security.jwt;

public class SecurityConstants {
    public static final String KEY = "SECRET_KEY";
    public static final long EXPIRATION_TIME = 14; // Days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

}