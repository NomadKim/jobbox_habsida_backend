package com.example.jobbox.inputModels;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class UpdateBannerInput {
    private  Long id;
    private String title;
    private Integer sort;
    private Long image_id;
    private Long service_id;
}
