package com.example.jobbox.service;

import com.example.jobbox.config.exceptions.ProfileNotFoundExeption;
import com.example.jobbox.inputModels.CreateProfileInput;
import com.example.jobbox.model.Profile;
import com.example.jobbox.model.User;
import com.example.jobbox.model.enums.ESort;
import com.example.jobbox.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository repository;

    public List<Profile> getAllByPaging(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        Pageable paging;
        if (sort.equals(ESort.ASC)){
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }

        Page<Profile> pagedResult = repository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Profile>();
        }
    }

    @Transactional
    public void update(User user, CreateProfileInput input) {
        Profile profile = getById(user.getProfile().getId());
        profile.setName(input.getName());
        profile.setBirthday(input.getBirthday());
        profile.setGender(input.getGender());
        profile.setPhone(input.getPhone());
        profile.setVisa(input.getVisa());
        profile.setAboutMe(input.getAboutMe());
        profile.setCar(input.getHasCar());
        profile.setUpdated_at(LocalDateTime.now());
        repository.saveAndFlush(profile);
    }

    public Profile getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProfileNotFoundExeption(id));
    }
}
