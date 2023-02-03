package com.example.jobbox.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FacebookLoginDto {
    private String id;
    private String email;
    private String name;
}
