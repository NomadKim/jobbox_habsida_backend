package com.example.jobbox.inputModels;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class UpdateCategoryInput {
    private Long id;
    private String title;
    private Long image_id;
    private Integer sort;
    private Boolean isActive;
}
