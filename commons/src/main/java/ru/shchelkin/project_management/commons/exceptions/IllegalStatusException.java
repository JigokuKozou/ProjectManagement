package ru.shchelkin.project_management.commons.exceptions;

public class IllegalStatusException extends IllegalStateException {
    public IllegalStatusException(String resource, String current, String value) {
        super("%s can't change status from \"%s\" to \"%s\".".formatted(resource, current, value));
    }
}
