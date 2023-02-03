package com.example.jobbox.inputModels;

import com.example.jobbox.model.enums.EVisa;
import com.example.jobbox.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class CreateProfileInput {
    private String name;
    private String birthday;
    private Gender gender;
    private String phone;
    private EVisa visa;
    private String aboutMe;
    private Boolean hasCar;
}
