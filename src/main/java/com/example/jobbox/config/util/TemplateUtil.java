package com.example.jobbox.config.util;

import com.example.jobbox.model.Template;

public class TemplateUtil {
    private TemplateUtil() {}

    public static Template toTemplate(Template defaultTemplate) {
        return Template.builder()
                .title(defaultTemplate.getTitle())
                .address(defaultTemplate.getAddress())
                .position(defaultTemplate.getPosition())
                .worktimeFrom(defaultTemplate.getWorktimeFrom())
                .worktimeTo(defaultTemplate.getWorktimeTo())
                .payment(defaultTemplate.getPayment())
                .overtimeFrom(defaultTemplate.getOvertimeFrom())
                .overtimeTo(defaultTemplate.getOvertimeTo())
                .overtimePay(defaultTemplate.getOvertimePay())
                .dailySum(EventUtil.dailySumCount(defaultTemplate.getIsPaidHourly(), defaultTemplate.getIsOvertimePaidHourly(), defaultTemplate.getWorktimeFrom(), defaultTemplate.getWorktimeTo(), defaultTemplate.getOvertimeFrom(), defaultTemplate.getOvertimeTo(), defaultTemplate.getPayment(), defaultTemplate.getOvertimePay()))
                .isPaidHourly(defaultTemplate.getIsPaidHourly())
                .isOvertimePaidHourly(defaultTemplate.getIsOvertimePaidHourly())
                .isDefault(false)
                .build();
    }
}
