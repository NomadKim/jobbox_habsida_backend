package com.example.jobbox.inputModels;

import com.example.jobbox.model.enums.ESocialType;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class CreateUserInput {
    private String email;
    private String password;
    private ESocialType socialType;
    private String socialToken;
}
