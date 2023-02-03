package com.example.jobbox.model;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String title;
    private String address;
    private String position;
    private String worktimeFrom;
    private String worktimeTo;
    private Integer payment;
    private String overtimeFrom;
    private String overtimeTo;
    private Integer overtimePay;
    private String about;
    private Float dailySum;
    private Integer hoursWorked;
    private Boolean isCompleted;
    private Boolean isPaid;
    private Boolean isPaidHourly;
    private Boolean isOvertimePaidHourly;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getPosition() {
        return position;
    }

    public String getWorktimeFrom() {
        return worktimeFrom;
    }

    public String getWorktimeTo() {
        return worktimeTo;
    }

    public Integer getPayment() {
        return payment;
    }

    public String getOvertimeFrom() {
        return overtimeFrom;
    }

    public String getOvertimeTo() {
        return overtimeTo;
    }

    public Integer getOvertimePay() {
        return overtimePay;
    }

    public String getAbout() {
        return about;
    }

    public Float getDailySum() {
        return dailySum;
    }

    public Integer getHoursWorked() {
        return hoursWorked;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public Boolean getPaidHourly() {
        return isPaidHourly;
    }

    public Boolean getOvertimePaidHourly() {
        return isOvertimePaidHourly;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public String getCreated_at() {
        return String.valueOf(created_at);
    }

    public String getUpdated_at() {
        return String.valueOf(updated_at);
    }

    public User getUser() {
        return user;
    }
}
