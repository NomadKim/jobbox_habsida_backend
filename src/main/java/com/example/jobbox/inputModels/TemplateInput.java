package com.example.jobbox.inputModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TemplateInput {
    private String title;
    private String address;
    private String position;
    private String from;
    private String to;
    private Integer payment;
    private String overtimeFrom;
    private String overtimeTo;
    private Integer overtimePay;
    private Boolean isPaidHourly;
    private Boolean isOvertimePaidHourly;
    private Float dailySum;
}
