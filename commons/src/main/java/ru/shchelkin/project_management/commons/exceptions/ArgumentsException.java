package ru.shchelkin.project_management.commons.exceptions;

import java.util.LinkedHashMap;
import java.util.Map;

public class ArgumentsException extends RuntimeException {
    private final Map<String, String> argumentsError;

    public ArgumentsException() {
        this.argumentsError = new LinkedHashMap<>();
    }

    public Map<String, String> getArgumentsError() {
        return argumentsError;
    }

    public ArgumentsException add(String argumentName, String message) {
        argumentsError.put(argumentName, message);
        return this;
    }
}
