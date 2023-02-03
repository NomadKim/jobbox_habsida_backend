package com.example.jobbox.config.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventUtil {
    private EventUtil() {

    }

    public static Float dailySumCount(boolean isPaidHourly, boolean isOvertimePaidHourly, String from, String to, String overtimeFrom, String overtimeTo, Integer pay, Integer overpay) {
        float sum = 0;
        if(isPaidHourly && isOvertimePaidHourly) {
            if(overtimeTo.isBlank() && overtimeFrom.isBlank()) {
                overtimeFrom = "00:00";
                overtimeTo = "00:00";
            }
            sum = dailySumCount(from, to, overtimeFrom, overtimeTo, pay, overpay);
        } else if(isPaidHourly) {
            sum = dailySumCount(from, to, "00:00", "00:00", pay, 0) + overpay;
        } else if(isOvertimePaidHourly) {
            sum = dailySumCount("00:00", "00:00", overtimeFrom, overtimeTo, 0, overpay) + pay;
        } else {
            sum = pay + overpay;
        }
        return sum;
    }

    public static Float dailySumCount(String from, String to, String overtimeFrom, String overtimeTo, Integer pay, Integer overpay) {
        int durationDay = durationInMinutes(from, to, "00:00", "00:00");
        int durationOver = durationInMinutes(overtimeFrom, overtimeTo, "00:00", "00:00");
        NumberFormat formatter = new DecimalFormat("#.00");
        return Float.valueOf(formatter.format((((pay / 60F) * (durationDay)) + ((overpay / 60F) * durationOver))).replaceAll(",", "."));
    }

    public static Integer durationInMinutes(String from, String to, String overtimeFrom, String overtimeTo) {
        long dayDuration;
        LocalTime localTimeFrom = LocalTime.parse(from, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime localTimeTo = LocalTime.parse(to, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime overFrom = LocalTime.parse("00:00", DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime overTo = LocalTime.parse("00:00", DateTimeFormatter.ofPattern("HH:mm"));
        if(!overtimeFrom.isBlank() && !overtimeFrom.isBlank()) {
            overFrom = LocalTime.parse(overtimeFrom, DateTimeFormatter.ofPattern("HH:mm"));
            overTo = LocalTime.parse(overtimeTo, DateTimeFormatter.ofPattern("HH:mm"));
        }
        if(localTimeFrom.isAfter(localTimeTo) && overFrom.isAfter(overTo)) {
            LocalDateTime dayFrom = LocalDateTime.parse("2020-01-01 " + from, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime dayTo = LocalDateTime.parse("2020-01-02 " + to, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime overtimeFromWithDate = LocalDateTime.parse("2020-01-01 " + overtimeFrom, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime overtimeToWithDate = LocalDateTime.parse("2020-01-02 " + overtimeTo, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            dayDuration = Duration.between(dayFrom, dayTo).toMinutes() + Duration.between(overtimeFromWithDate, overtimeToWithDate).toMinutes();
        } else if(localTimeFrom.isAfter(localTimeTo)) {
            LocalDateTime dayFrom = LocalDateTime.parse("2020-01-01 " + from, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime dayTo = LocalDateTime.parse("2020-01-02 " + to, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            dayDuration = Duration.between(dayFrom, dayTo).toMinutes() + Duration.between(overFrom, overTo).toMinutes();
        } else if(overFrom.isAfter(overTo)) {
            LocalDateTime overtimeFromWithDate = LocalDateTime.parse("2020-01-01 " + overtimeFrom, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime overtimeToWithDate = LocalDateTime.parse("2020-01-02 " + overtimeTo, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            dayDuration = Duration.between(localTimeFrom, localTimeTo).toMinutes() + Duration.between(overtimeFromWithDate, overtimeToWithDate).toMinutes();
        } else {
            dayDuration = Duration.between(localTimeFrom, localTimeTo).toMinutes() + Duration.between(overFrom, overTo).toMinutes();
        }
        return (int) dayDuration;
    }

    public static List<String> checkEveryWeek(String startDate, List<Integer> listWeek) {
        LocalDate date = LocalDate.parse(startDate);
        List<String> listOfDaysByWeek = new ArrayList<>();
        LocalDate finalDate = date.plusMonths(1);

        while (!date.isAfter(finalDate)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            for (Integer i : listWeek) {
                if(dayOfWeek.equals(DayOfWeek.of(i))) {
                    listOfDaysByWeek.add(date.toString());
                    break;
                }
            }
            date = date.plusDays(1);
        }
        return listOfDaysByWeek;
    }

    public static List<String> checkEveryDay(String startDate) {
        LocalDate date = LocalDate.parse(startDate);
        List<String> listOfDates = new ArrayList<>();
        LocalDate finalDate = date.plusMonths(1);

        while (!date.isAfter(finalDate)) {
            listOfDates.add(date.toString());

            date = date.plusDays(1);
        }
        return listOfDates;
    }
}
