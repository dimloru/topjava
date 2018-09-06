package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeFormatter implements Formatter<LocalTime> {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_TIME;

    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return (text == null || text.isEmpty()) ? null : LocalTime.parse(text, formatter);
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return object == null ? "" : object.format(formatter);
    }
}
