package com.example.jobbox.model;

import com.example.jobbox.model.enums.EVisa;
import com.example.jobbox.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_posts")
@Getter
@Setter
@NamedEntityGraph(name = "JobPost.UsersFields",
        attributeNodes = {@NamedAttributeNode("invited"),
                @NamedAttributeNode("postedUser"),
                @NamedAttributeNode("usersEmployed"),
                @NamedAttributeNode("applicants")
        })

public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String date;
    @Column
    private String title;
    @Column
    private String address;
    @Column
    private String position;
    @Column
    private String workTimeFrom;
    @Column
    private String workTimeTo;
    @Column
    private Float payment;
    @Column
    private Float overtimePay;
    @Column
    private Float dailySum;
    @Column
    private Boolean isPaidHourly;
    @Column
    private Boolean isOvertimePaidHourly;
    @Column
    private String overtimeFrom;
    @Column
    private String overtimeTo;
    @Column
    private Integer hoursWorked;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;
    @Column
    private Boolean isPositionOpen;
    @Column
    private Integer totalRecruits;
    @Column
    private Integer applicationsReceived;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User postedUser;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "applicants",
            joinColumns = { @JoinColumn(name = "jobPostId") },
            inverseJoinColumns = { @JoinColumn(name = "userId") }
    )
    private Set<User> applicants;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usersEmployed",
            joinColumns = { @JoinColumn(name = "jobPostId") },
            inverseJoinColumns = { @JoinColumn(name = "userId") }
    )
    private Set<User> usersEmployed;
    @Column
    private Gender gender;
    @Column
    private EVisa visa;
    @Column
    private String age;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "invited_users",
            joinColumns = { @JoinColumn(name = "jobPostId") },
            inverseJoinColumns = { @JoinColumn(name = "userId") }
    )
    private Set<User> invited;
}
