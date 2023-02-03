package com.example.jobbox.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.jobbox.model.Banner;
import com.example.jobbox.model.File;
import com.example.jobbox.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BannerResolver implements GraphQLResolver<Banner> {
    private final FileService fileService;

    public File getImage(Banner banner) {
        return fileService.getById(banner.getImage().getId());
    }
}
