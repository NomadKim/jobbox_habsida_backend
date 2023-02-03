package com.example.jobbox.service;

import com.example.jobbox.config.exceptions.TemplateNotFoundException;
import com.example.jobbox.config.util.EventUtil;
import com.example.jobbox.config.util.TemplateUtil;
import com.example.jobbox.inputModels.TemplateInput;
import com.example.jobbox.model.Template;
import com.example.jobbox.model.User;
import com.example.jobbox.model.enums.ESort;
import com.example.jobbox.repository.TemplateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class TemplateService {
    private final TemplateRepository templateRepository;
    private final UserService userService;

    public TemplateService(TemplateRepository templateRepository, UserService userService) {
        this.templateRepository = templateRepository;
        this.userService = userService;
    }

    public Template getTemplate(Long id) {
        return templateRepository.findById(id).orElseThrow(() -> new TemplateNotFoundException("Template not found"));
    }
    public List<Template> getAllTemplates(Long id) {
        return templateRepository.getTemplateByUser(id);
    }

    public List<Template> getDefaultTemplates() {
        return templateRepository.getTemplateByIsDefault();
    }

    public List<Template> getAllTemplates(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        Pageable paging;
        if (sort.equals(ESort.ASC)) {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }

        Page<Template> pagedResult = templateRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Template>();
        }
    }

    @Transactional
    public Template createTemplate(TemplateInput templateInput) {
        HashSet<User> users = new HashSet<>();
        users.add(userService.getCurrentUser().get());
        Template template = Template.builder()
                .title(templateInput.getTitle())
                .address(templateInput.getAddress())
                .position(templateInput.getPosition())
                .worktimeFrom(templateInput.getFrom())
                .worktimeTo(templateInput.getTo())
                .payment(templateInput.getPayment())
                .overtimeFrom(templateInput.getOvertimeFrom())
                .overtimeTo(templateInput.getOvertimeTo())
                .overtimePay(templateInput.getOvertimePay())
                .dailySum(EventUtil.dailySumCount(templateInput.getIsPaidHourly(), templateInput.getIsOvertimePaidHourly(), templateInput.getFrom(), templateInput.getTo(), templateInput.getOvertimeFrom(), templateInput.getOvertimeTo(), templateInput.getPayment(), templateInput.getOvertimePay()))
                .isPaidHourly(templateInput.getIsPaidHourly())
                .isOvertimePaidHourly(templateInput.getIsOvertimePaidHourly())
                .user(userService.getCurrentUser().get())
                .isDefault(false)
                .build();
        return templateRepository.saveAndFlush(template);
    }

    public Template createTemplateFromDefault(Long defaultTemplateId) {
        Template template = TemplateUtil.toTemplate(getTemplate(defaultTemplateId));
        template.setUser(userService.getCurrentUser().get());
        return templateRepository.save(template);
    }

    @Transactional
    public Template createDefaultTemplate(TemplateInput templateInput) {
        Template templateDefault = Template.builder()
                .title(templateInput.getTitle())
                .address(templateInput.getAddress())
                .position(templateInput.getPosition())
                .worktimeFrom(templateInput.getFrom())
                .worktimeTo(templateInput.getTo())
                .payment(templateInput.getPayment())
                .overtimeFrom(templateInput.getOvertimeFrom())
                .overtimeTo(templateInput.getOvertimeTo())
                .overtimePay(templateInput.getOvertimePay())
                .dailySum(EventUtil.dailySumCount(templateInput.getIsPaidHourly(), templateInput.getIsOvertimePaidHourly(), templateInput.getFrom(), templateInput.getTo(), templateInput.getOvertimeFrom(), templateInput.getOvertimeTo(), templateInput.getPayment(), templateInput.getOvertimePay()))
                .isPaidHourly(templateInput.getIsPaidHourly())
                .isOvertimePaidHourly(templateInput.getIsOvertimePaidHourly())
                .isDefault(true)
                .build();

        return templateRepository.saveAndFlush(templateDefault);
    }

    @Transactional
    public Template updateTemplate(Long id, TemplateInput templateInput) {
        Template template = getTemplate(id);
        template.setTitle(templateInput.getTitle());
        template.setAddress(templateInput.getAddress());
        template.setPosition(templateInput.getPosition());
        template.setWorktimeFrom(templateInput.getFrom());
        template.setWorktimeTo(templateInput.getTo());
        template.setPayment(templateInput.getPayment());
        template.setOvertimeFrom(templateInput.getOvertimeFrom());
        template.setOvertimeTo(templateInput.getOvertimeTo());
        template.setOvertimePay(templateInput.getOvertimePay());
        template.setDailySum(EventUtil.dailySumCount(templateInput.getIsPaidHourly(), templateInput.getIsOvertimePaidHourly(), templateInput.getFrom(), templateInput.getTo(), templateInput.getOvertimeFrom(), templateInput.getOvertimeTo(), templateInput.getPayment(), templateInput.getOvertimePay()));
        template.setIsPaidHourly(templateInput.getIsPaidHourly());
        template.setIsOvertimePaidHourly(templateInput.getIsOvertimePaidHourly());

        return templateRepository.saveAndFlush(template);
    }

    @Transactional
    public Template updateDefaultTemplate(Long id, TemplateInput input) {
        Template template = getTemplate(id);
        template.setTitle(input.getTitle());
        template.setAddress(input.getAddress());
        template.setPosition(input.getPosition());
        template.setWorktimeFrom(input.getFrom());
        template.setWorktimeTo(input.getTo());
        template.setPayment(input.getPayment());
        template.setOvertimeFrom(input.getOvertimeFrom());
        template.setOvertimeTo(input.getOvertimeTo());
        template.setOvertimePay(input.getOvertimePay());
        template.setDailySum(EventUtil.dailySumCount(input.getIsPaidHourly(), input.getIsOvertimePaidHourly(), input.getFrom(), input.getTo(), input.getOvertimeFrom(), input.getOvertimeTo(), input.getPayment(), input.getOvertimePay()));
        template.setIsPaidHourly(input.getIsPaidHourly());
        template.setIsOvertimePaidHourly(input.getIsOvertimePaidHourly());
        template.setIsDefault(true);

        return template;
    }

    public void deleteTemplate(Long id) {
        templateRepository.deleteById(id);
    }
}
