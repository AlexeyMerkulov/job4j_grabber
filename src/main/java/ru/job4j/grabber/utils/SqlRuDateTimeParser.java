package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Map<String, String> MONTHS = Map.ofEntries(Map.entry("янв", "JANUARY"),
            Map.entry("фев", "FEBRUARY"), Map.entry("мар", "MARCH"), Map.entry("апр", "APRIL"),
            Map.entry("май", "MAY"), Map.entry("июн", "JUNE"), Map.entry("июл", "JULY"),
            Map.entry("авг", "AUGUST"), Map.entry("сен", "SEPTEMBER"), Map.entry("окт", "OCTOBER"),
            Map.entry("ноя", "NOVEMBER"), Map.entry("дек", "DECEMBER"));

    @Override
    public LocalDateTime parse(String parse) {
        String[] dateTimeArray = parse.split(", ");
        String[] timeArray = dateTimeArray[1].split(":");
        LocalTime time = LocalTime.of(Integer.parseInt(timeArray[0]), Integer.parseInt(timeArray[1]));
        LocalDate date;
        if (dateTimeArray[0].equals("сегодня")) {
            date = LocalDate.now();
        } else if (dateTimeArray[0].equals("вчера")) {
            date = LocalDate.now().minusDays(1);
        } else {
            String[] dateArray = dateTimeArray[0].split(" ");
            int day = Integer.parseInt(dateArray[0]);
            String month = MONTHS.get(dateArray[1]);
            int year = 2000 + Integer.parseInt(dateArray[2]);
            date = LocalDate.of(year, Month.valueOf(month), day);
        }
        return LocalDateTime.of(date, time);
    }
}