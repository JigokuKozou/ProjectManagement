package ru.shchelkin.project_management.commons.exceptions;

import org.springframework.util.StringUtils;

public class BlankException extends ArgumentException {
    public BlankException(String argumentName) {
        this(argumentName, StringUtils.capitalize(argumentName));
    }
    public BlankException(String argumentName, String displayName) {
        super(argumentName, displayName + " should not be blank.");
    }
}
