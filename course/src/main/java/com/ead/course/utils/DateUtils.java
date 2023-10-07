package com.ead.course.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtils {
    public String parseDate(LocalDateTime data) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(data);
    }
}
