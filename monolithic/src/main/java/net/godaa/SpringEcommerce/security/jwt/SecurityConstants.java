package net.godaa.SpringEcommerce.security.jwt;

public class SecurityConstants {
    public static final String KEY = "SECRET_KEY";
    public static final long EXPIRATION_TIME = 14; // Days

    public static final String BASE_URL = "http://localhost:8080";


    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_EXAMPLE = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1lZGdvZGFhIiwiaWF0IjoxNjc2NTk4OTc3LCJleHAiOjE2NzkxOTA5Nzd9.0WE2DkY5Rg57YNT4XMGPSSdUDdNssrwJA82a941xB5w";
    public static final String ID_EXAMPLE = "5552343d-8062-4559-85a3-e622b874f0c9";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

}