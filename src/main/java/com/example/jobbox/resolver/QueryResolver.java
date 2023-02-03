package com.example.jobbox.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.jobbox.inputModels.EventsOutput;
import com.example.jobbox.inputModels.StatisticsOutput;
import com.example.jobbox.model.*;
import com.example.jobbox.model.enums.ESort;
import com.example.jobbox.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class QueryResolver implements GraphQLQueryResolver {

    private final UserService service;
    private final ProfileService profileService;
    private final EventService eventService;
    private final TemplateService templateService;
    private final CategoryService categoryService;
    private final ServService servService;
    private final BannerService bannerService;
    private final FileService fileService;
    private final JobPostService jobPostService;


    public User getUser(Long id) {
        return service.getUser(id);
    }
    public List<User> getUsersByPaging(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        return service.getAllByPaging(pageNo, pageSize, sortBy, sort);
    }
    public Boolean existsUserByEmail(String email) {
        return service.existsUserByEmail(email);
    }


    public Profile getProfile(Long id) {
        return profileService.getById(id);
    }
    public List<Profile> getProfilesByPaging(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        return profileService.getAllByPaging(pageNo, pageSize, sortBy, sort);
    }

    public List<Template> getTemplates() {
        return templateService.getAllTemplates(service.getCurrentUser().get().getId());
    }

    public List<Template> getDefaultTemplates() {
        return templateService.getDefaultTemplates();
    }

    public Template getTemplate(Long id) {
        return templateService.getTemplate(id);
    }

    public Event getEvent(Long id) {
        return eventService.getById(id);
    }
    public List<Event> getEventsByPaging(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        return eventService.getAllByPaging(pageNo, pageSize, sortBy, sort);
    }

    public EventsOutput eventsByDate(String start, String end) {
        return eventService.eventsByDate(start, end);
    }

    public EventsOutput eventsByDateAndCompleted(String from, String to) {
        return eventService.eventsByCompleted(from, to);
    }

    public List<StatisticsOutput> statisticsByTemplate(String from, String to) {
        return eventService.eventsByTemplate(from, to);
    }

    public Optional<User> getCurrentUser() {
        return service.getCurrentUser();
    }

    public Optional<Role> getRole(Long id) {
        return service.getRole(id);
    }
    public List<Role> getRolesByPaging(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        return service.getAllRoles(pageNo, pageSize, sortBy, sort);
    }

    public Category getCategory(Long id) {
        return categoryService.getById(id);
    }

    public List<Category> getCategoriesByPaging(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        return categoryService.getAllByPaging(pageNo, pageSize, sortBy, sort);
    }

    public Service getService(Long id) {
        return servService.getById(id);
    }
    public List<Service> getServicesByPaging(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        return servService.getAllByPaging(pageNo, pageSize, sortBy, sort);
    }
    public List<Service> getServicesByCategoryId(Long id, Integer page, Integer size) {
        return servService.getAllByCategoryIdWithPagination(id, page, size);
    }
    public List<Service> searchByTitle(String title, Integer page, Integer size) {
        return servService.searchByTitle(title, page, size);
    }

    public Banner getBanner(Long id) {
        return bannerService.getById(id);
    }
    public List<Banner> getBannersByPaging(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        return bannerService.getAllByPaging(pageNo, pageSize, sortBy, sort);
    }

    public File getFile(Long id) {
        return fileService.getById(id);
    }
    public List<File> getFilesByPaging(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        return fileService.getAllByPaging(pageNo, pageSize, sortBy, sort);
    }

    public JobPost getJobPost(Long id){
        return jobPostService.getById(id);
    }


}
