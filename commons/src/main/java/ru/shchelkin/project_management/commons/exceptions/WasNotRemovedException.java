package ru.shchelkin.project_management.commons.exceptions;

public class WasNotRemovedException extends RuntimeException {
    public WasNotRemovedException(String resource) {
        super(resource + " was not removed");
    }
}
