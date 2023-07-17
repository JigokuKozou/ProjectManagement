package ru.shchelkin.project_management.commons.util;

import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

public class CustomStringUtils {
    public static String strip(String string) {
        return Objects.isNull(string) ? Strings.EMPTY : string.strip();
    }
}
