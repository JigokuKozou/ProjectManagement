package ru.shchelkin.project_management.commons.exceptions;

import org.springframework.validation.BindingResult;

public class NotValidException extends RuntimeException {
    private final BindingResult bindingResult;

    public NotValidException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
