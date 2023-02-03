package com.example.jobbox.inputModels;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class UpdateServiceInput {
    private Long id;
    private String title;
    private String phone;
    private String site;
    private String address;
    private String time;
    private String description;
    private Integer sort;
    private Boolean isActive;
    private Long image_id;
    private Float longitude;
    private Float latitude;
    private Long category_id;
}
