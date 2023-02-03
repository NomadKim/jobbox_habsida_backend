package com.example.jobbox.service;

import com.example.jobbox.inputModels.CreateCategoryInput;
import com.example.jobbox.inputModels.UpdateCategoryInput;
import com.example.jobbox.model.Category;
import com.example.jobbox.model.File;
import com.example.jobbox.model.enums.ESort;
import com.example.jobbox.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    @Transactional
    public Category createCategory(CreateCategoryInput input, File fileImage) {
        Category category = new Category();
        category.setTitle(input.getTitle());
        category.setImage(fileImage);
        category.setSort(input.getSort());
        category.setIsActive(input.getIsActive());
        category.setCreated_at(LocalDateTime.now());

        return repository.saveAndFlush(category);
    }
    @Transactional
    public Category updateCategory(UpdateCategoryInput input, File fileImage) {
        Category category = getById(input.getId());
        category.setTitle(input.getTitle());
        category.setImage(fileImage);
        category.setSort(input.getSort());
        category.setIsActive(input.getIsActive());
        category.setUpdated_at(LocalDateTime.now());

        return repository.saveAndFlush(category);
    }
    public Category getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public List<Category> getAllByPaging(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        Pageable paging;
        if (sort.equals(ESort.ASC)){
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }

        Page<Category> pagedResult = repository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Category>();
        }
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
