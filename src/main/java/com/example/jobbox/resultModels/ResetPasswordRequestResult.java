package com.example.jobbox.resultModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequestResult {
    private Integer hashCode;
    private String expireTime;
}
