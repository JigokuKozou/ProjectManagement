package ru.shchelkin.project_management.commons.exceptions.auth;

public class JwtInvalidException extends RuntimeException {
    public JwtInvalidException(String message) {
        super(message);
    }

    public JwtInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
