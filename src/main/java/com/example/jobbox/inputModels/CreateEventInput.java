package com.example.jobbox.inputModels;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateEventInput {
    private String date;
    private String title;
    private String address;
    private String position;
    private String to;
    private String from;
    private Integer payment;
    private String overtimeFrom;
    private String overtimeTo;
    private Integer overtimePay;
    private String about;
    private Boolean isCompleted;
    private Boolean isPaid;
    private Boolean isPaidHourly;
    private Boolean isOvertimePaidHourly;
}
