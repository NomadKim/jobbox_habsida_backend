package com.example.jobbox.service;

import com.example.jobbox.inputModels.UpdateServiceInput;
import com.example.jobbox.model.Category;
import com.example.jobbox.model.File;
import com.example.jobbox.model.Service;
import com.example.jobbox.inputModels.CreateServiceInput;
import com.example.jobbox.model.enums.ESort;
import com.example.jobbox.repository.CategoryRepository;
import com.example.jobbox.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServService {
    private final ServiceRepository repository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Service createService(CreateServiceInput input, File fileImage) {
        Service service = new Service();
        service.setTitle(input.getTitle());
        service.setPhone(input.getPhone());
        service.setSite(input.getSite());
        service.setAddress(input.getAddress());
        service.setTime(input.getTime());
        service.setDescription(input.getDescription());
        service.setSort(input.getSort());
        service.setIsActive(input.getIsActive());
        service.setImage(fileImage);

        service.setCreated_at(LocalDateTime.now());
        service.setLongitude(input.getLongitude());
        service.setLatitude(input.getLatitude());

        Category category = categoryRepository.findById(input.getCategory_id()).orElseThrow(() -> new EntityNotFoundException());
        service.setCategory(category);

        return repository.saveAndFlush(service);
    }

    @Transactional
    public Service updateService(UpdateServiceInput input, File fileimage) {
        Service service = getById(input.getId());
        service.setTitle(input.getTitle());
        service.setPhone(input.getPhone());
        service.setSite(input.getSite());
        service.setAddress(input.getAddress());
        service.setTime(input.getTime());
        service.setDescription(input.getDescription());
        service.setSort(input.getSort());
        service.setIsActive(input.getIsActive());
        service.setImage(fileimage);

        service.setUpdated_at(LocalDateTime.now());
        service.setLongitude(input.getLongitude());
        service.setLatitude(input.getLatitude());

        Category category = categoryRepository.findById(input.getCategory_id()).orElseThrow(() -> new EntityNotFoundException());
        service.setCategory(category);

        return repository.saveAndFlush(service);
    }

    public Service getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public List<Service> getAllByPaging(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        Pageable paging;
        if (sort.equals(ESort.ASC)){
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }

        Page<Service> pagedResult = repository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Service>();
        }
    }

    public List<Service> searchByTitle (String title, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("id").descending());

        Page<Service> pagedResult = repository.searchWithPagination(title, pageable);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Service>();
        }
    }

    public List<Service> getAllByCategoryIdWithPagination(Long id, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Service> pagedResult = repository.findByCategoryIdWithPagination(id, pageable);
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Service>();
        }
    }
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
