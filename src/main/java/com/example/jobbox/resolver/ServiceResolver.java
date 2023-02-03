package com.example.jobbox.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.jobbox.model.Banner;
import com.example.jobbox.model.File;
import com.example.jobbox.model.Service;
import com.example.jobbox.repository.BannerRepository;
import com.example.jobbox.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceResolver implements GraphQLResolver<Service> {
    private final BannerRepository bannerRepository;
    private final FileService fileService;

    public File getImage(Service service) {
        return fileService.getById(service.getImage().getId());
    }

    public List<Banner> getBanners(Service service) {
        return bannerRepository.findBannersByServiceId(service.getId());
    }
}
