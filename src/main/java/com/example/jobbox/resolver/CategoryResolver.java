package com.example.jobbox.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.jobbox.model.Category;
import com.example.jobbox.model.File;
import com.example.jobbox.model.Service;
import com.example.jobbox.repository.ServiceRepository;
import com.example.jobbox.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class  CategoryResolver implements GraphQLResolver<Category> {
    private final FileService fileService;
    private final ServiceRepository serviceRepository;

    public File getImage(Category category) {
        return fileService.getById(category.getImage().getId());
    }

    public List<Service> getServices(Category category) {
        return serviceRepository.findServicesByCategoryId(category.getId());
    }
}
