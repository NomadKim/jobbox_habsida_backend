package com.example.jobbox.inputModels;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdatePasswordInput {
    private String originalPassword;
    private String newPassword;
}
