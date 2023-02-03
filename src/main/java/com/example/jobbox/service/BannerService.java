package com.example.jobbox.service;

import com.example.jobbox.inputModels.CreateBannerInput;
import com.example.jobbox.inputModels.UpdateBannerInput;
import com.example.jobbox.model.Banner;
import com.example.jobbox.model.File;
import com.example.jobbox.model.Service;
import com.example.jobbox.model.enums.ESort;
import com.example.jobbox.repository.BannerRepository;
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
public class BannerService {
    private final BannerRepository repository;
    private final ServiceRepository serviceRepository;

    @Transactional
    public Banner createBanner(CreateBannerInput input, File fileImage) {
        Banner banner = new Banner();
        banner.setTitle(input.getTitle());
        banner.setSort(input.getSort());
        banner.setImage(fileImage);
        banner.setCreated_at(LocalDateTime.now());

        Service service = serviceRepository.findById(input.getService_id()).orElseThrow(() -> new EntityNotFoundException());
        banner.setService(service);

        return repository.saveAndFlush(banner);
    }
    @Transactional
    public Banner updateBanner(UpdateBannerInput input, File fileImage) {
        Banner banner = getById(input.getId());
        banner.setTitle(input.getTitle());
        banner.setSort(input.getSort());
        banner.setImage(fileImage);
        banner.setUpdated_at(LocalDateTime.now());

        Service service = serviceRepository.findById(input.getService_id()).orElseThrow(() -> new EntityNotFoundException());
        banner.setService(service);

        return repository.saveAndFlush(banner);
    }
    public Banner getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public List<Banner> getAllByPaging(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        Pageable paging;
        if (sort.equals(ESort.ASC)){
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }

        Page<Banner> pagedResult = repository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Banner>();
        }
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
