package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter implements Formatter<LocalDate> {
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return (text == null || text.isEmpty()) ? null : LocalDate.parse(text, formatter);
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object == null ? "" : object.format(formatter);
    }
}
