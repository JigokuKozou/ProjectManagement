package ru.shchelkin.project_management.commons.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class CustomTimeUtils {
    public static LocalDateTime nowUtc() {
        return ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }
}
