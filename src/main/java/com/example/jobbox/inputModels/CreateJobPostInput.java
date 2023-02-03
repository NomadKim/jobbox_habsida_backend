package com.example.jobbox.inputModels;

import com.example.jobbox.model.User;
import com.example.jobbox.model.enums.EVisa;
import com.example.jobbox.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class CreateJobPostInput {

        private String date;
        private String title;
        private String address;
        private String position;
        private String workTimeFrom;
        private String workTimeTo;
        private Float payment;
        private Float overtimePay;
        private String overtimeFrom;
        private String overtimeTo;
        private Boolean isPositionOpen;
        private Integer totalRecruits;
        private Boolean isPaidHourly;
        private Boolean isOvertimePaidHourly;
        private Gender gender;
        private EVisa visa;
        private String age;
        private List<String> invitedUsersByPhoneNumber;
}
