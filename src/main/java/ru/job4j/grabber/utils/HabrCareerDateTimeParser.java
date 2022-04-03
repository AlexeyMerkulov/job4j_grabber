package ru.job4j.grabber.utils;

import java.time.LocalDateTime;

public class HabrCareerDateTimeParser implements DateTimeParser {
    @Override
    public LocalDateTime parse(String parse) {
        String[] array = parse.split("\\+");
        return LocalDateTime.parse(array[0]);
    }
}
