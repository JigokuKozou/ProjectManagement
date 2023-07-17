package ru.shchelkin.project_management.commons.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String resource) {
        super(resource + " was not found.");
    }
}
