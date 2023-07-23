package ru.shchelkin.project_management.commons.util;

import java.util.Objects;

public class CustomStringUtils {
    public static final String EMPTY_STRING = "";

    public static String strip(String string) {
        return Objects.isNull(string) ? EMPTY_STRING : string.strip();
    }
}
