package com.example.date;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;

public class USLocalDateFormatter implements Formatter<LocalDate> {

    public static final String US_PATTERN = "MM/dd/yy";
    public static final String DE_PATTERN = "dd/MM/yyyy";

    @Override
    public String print(LocalDate object, Locale locale) {

	return DateTimeFormatter.ofPattern(DE_PATTERN).format(object);
    }

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
	return LocalDate.parse(text, DateTimeFormatter.ofPattern(DE_PATTERN));
    }

    public static String getPattern(Locale locale) {
	return Locale.US.getCountry().equals(locale) ? US_PATTERN : DE_PATTERN;
    }

}
