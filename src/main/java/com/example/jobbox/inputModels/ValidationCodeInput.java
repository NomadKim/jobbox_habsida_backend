package com.example.jobbox.inputModels;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class ValidationCodeInput {
    private String email;
    private Integer hashCode;
    private String expirationTime;
    private Integer verificationCode;
}
