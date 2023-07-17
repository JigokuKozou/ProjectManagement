package ru.shchelkin.project_management.commons.exceptions;

import org.springframework.util.StringUtils;

public class NullException extends ArgumentException {
    public NullException(String argumentName) {
        this(argumentName, StringUtils.capitalize(argumentName));
    }
    public NullException(String argumentName, String displayName) {
        super(argumentName, displayName + " should not be null.");
    }
}
