package com.example.jobbox.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleLoginDto {
    private String id;
    private String email;
    private String verified_email;
    private String name;
    private String picture;
    private String given_name;
    private String family_name;
    private String locale;
    private String hd;
}
