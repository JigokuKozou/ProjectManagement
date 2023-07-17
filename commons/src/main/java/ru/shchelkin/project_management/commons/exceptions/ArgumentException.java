package ru.shchelkin.project_management.commons.exceptions;

public class ArgumentException extends RuntimeException {
    private final String argumentName;

    private final String message;

    public ArgumentException(String argumentName, String message) {
        this.argumentName = argumentName;
        this.message = message;
    }

    public String getArgumentName() {
        return argumentName;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
