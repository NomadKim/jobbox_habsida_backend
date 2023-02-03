package com.example.jobbox.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "templates")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String address;
    private String position;
    private String worktimeFrom;
    private String worktimeTo;
    private Integer payment;
    private String overtimeFrom;
    private String overtimeTo;
    private Integer overtimePay;
    private Float dailySum;
    private Boolean isPaidHourly;
    private Boolean isOvertimePaidHourly;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Boolean isDefault;

    @OneToMany(mappedBy = "template")
    private Set<Event> events;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
