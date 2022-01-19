package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final LocalDateTime MIN_DATE = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime Max_DATE = LocalDateTime.of(3000, 1, 1, 0, 0);

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime atStartOfDayOrMin(@Nullable LocalDate startDate) {
        return startDate != null ? startDate.atStartOfDay() : MIN_DATE;
    }

    public static LocalDateTime atStartOfNextDayOrMax(@Nullable LocalDate endDate) {
        return endDate != null ? endDate.plus(1, ChronoUnit.DAYS).atStartOfDay() : Max_DATE;
    }
}
