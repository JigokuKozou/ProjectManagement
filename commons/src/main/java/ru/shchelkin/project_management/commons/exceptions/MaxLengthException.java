package ru.shchelkin.project_management.commons.exceptions;

import org.springframework.util.StringUtils;

public class MaxLengthException extends ArgumentException {
    public MaxLengthException(String argumentName, int maxLength) {
        this(argumentName, maxLength, StringUtils.capitalize(argumentName));
    }

    public MaxLengthException(String argumentName, int maxLength, String displayName) {
        super(argumentName, "%s should not be more than %d symbols."
                .formatted(displayName, maxLength));
    }
}
