package ru.shchelkin.project_management.commons.exceptions;

import org.springframework.util.StringUtils;

public class NotUniqueException extends ArgumentException {
    public NotUniqueException(String argumentName) {
        this(argumentName, StringUtils.capitalize(argumentName));
    }

    public NotUniqueException(String argumentName, String displayName) {
        super(argumentName, displayName + " should be unique.");
    }
}
