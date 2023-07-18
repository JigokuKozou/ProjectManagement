package ru.shchelkin.project_management.commons.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CustomTimeUtils {
    public static LocalDateTime nowUtc() {
        return LocalDateTime.now().atZone(ZoneOffset.UTC).toLocalDateTime();
    }
}
