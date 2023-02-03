package com.example.jobbox.service;

import com.example.jobbox.config.util.EventUtil;
import com.example.jobbox.inputModels.CreateEventInput;
import com.example.jobbox.inputModels.EventsOutput;
import com.example.jobbox.inputModels.StatisticsOutput;
import com.example.jobbox.model.Event;
import com.example.jobbox.model.enums.ESort;
import com.example.jobbox.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    @Autowired
    private final EventRepository repository;

    @Autowired
    private final TemplateService templateService;

    @Autowired
    private UserService userService;

    @Transactional
    public Event createEvent(Long templateId, CreateEventInput eventInput) {
        Event event = new Event();
        event.setDate(eventInput.getDate());
        event.setTitle(eventInput.getTitle());
        event.setAddress(eventInput.getAddress());
        event.setPosition(eventInput.getPosition());
        event.setWorktimeFrom(eventInput.getFrom());
        event.setWorktimeTo(eventInput.getTo());
        event.setPayment(eventInput.getPayment());
        event.setOvertimeFrom(eventInput.getOvertimeFrom());
        event.setOvertimeTo(eventInput.getOvertimeTo());
        event.setOvertimePay(eventInput.getOvertimePay());
        event.setAbout(eventInput.getAbout());
        event.setIsCompleted(eventInput.getIsCompleted());
        event.setIsPaid(eventInput.getIsPaid());
        event.setIsPaidHourly(eventInput.getIsPaidHourly());
        event.setIsOvertimePaidHourly(eventInput.getIsOvertimePaidHourly());
        event.setCreated_at(LocalDateTime.now());
        event.setDailySum(EventUtil.dailySumCount(eventInput.getIsPaidHourly(), eventInput.getIsOvertimePaidHourly(), eventInput.getFrom(), eventInput.getTo(), eventInput.getOvertimeFrom(), eventInput.getOvertimeTo(), eventInput.getPayment(), eventInput.getOvertimePay()));
        event.setHoursWorked(EventUtil.durationInMinutes(eventInput.getFrom(), eventInput.getTo(), eventInput.getOvertimeFrom(), eventInput.getOvertimeTo()));
        event.setTemplate(templateService.getTemplate(templateId));
        event.setUser(userService.getCurrentUser().orElse(null));
        return repository.saveAndFlush(event);
    }

    public String createDailyEvent(Long templateId, CreateEventInput eventInput) {
        List<String> dates = EventUtil.checkEveryDay(eventInput.getDate());
        for(String date : dates) {
            eventInput.setDate(date);
            createEvent(templateId, eventInput);
        }
        return "done";
    }

    public String createWeeklyEvent(Long templateId, CreateEventInput eventInput, List<Integer> days) {
        List<String> dates = EventUtil.checkEveryWeek(eventInput.getDate(), days);
        for(String date : dates) {
            eventInput.setDate(date);
            createEvent(templateId, eventInput);
        }
        return "done";
    }

    @Transactional
    public Event updateEvent(Long id, CreateEventInput eventInput) {
        Event event = getById(id);
        event.setDate(eventInput.getDate());
        event.setTitle(eventInput.getTitle());
        event.setAddress(eventInput.getAddress());
        event.setPosition(eventInput.getPosition());
        event.setWorktimeFrom(eventInput.getFrom());
        event.setWorktimeTo(eventInput.getTo());
        event.setPayment(eventInput.getPayment());
        event.setOvertimeFrom(eventInput.getOvertimeFrom());
        event.setOvertimeTo(eventInput.getOvertimeTo());
        event.setOvertimePay(eventInput.getOvertimePay());
        event.setAbout(eventInput.getAbout());
        event.setIsCompleted(eventInput.getIsCompleted());
        event.setIsPaid(eventInput.getIsPaid());
        event.setIsPaidHourly(eventInput.getIsPaidHourly());
        event.setIsOvertimePaidHourly(eventInput.getIsOvertimePaidHourly());
        event.setUpdated_at(LocalDateTime.now());
        event.setDailySum(EventUtil.dailySumCount(eventInput.getIsPaidHourly(), eventInput.getIsOvertimePaidHourly(), eventInput.getFrom(), eventInput.getTo(), eventInput.getOvertimeFrom(), eventInput.getOvertimeTo(), eventInput.getPayment(), eventInput.getOvertimePay()));
        event.setHoursWorked(EventUtil.durationInMinutes(eventInput.getFrom(), eventInput.getTo(), eventInput.getOvertimeFrom(), eventInput.getOvertimeTo()));

        event.setUser(userService.getCurrentUser().orElse(null));
        return repository.saveAndFlush(event);
    }

    public EventsOutput eventsByDate(String from, String to) {
        EventsOutput output = new EventsOutput();
        output.setEvents(repository.getEventsFromTo(from, to, userService.getCurrentUser().get().getId()));
        output.setEarned(repository.earned(from, to, userService.getCurrentUser().get().getId()));
        output.setHoursWorked(repository.hoursWorked(from, to, userService.getCurrentUser().get().getId()));
        return output;
    }

    public EventsOutput eventsByCompleted(String from, String to) {
        EventsOutput output = new EventsOutput();
        output.setEvents(new ArrayList<>());
        output.setEarned(repository.getEarnedByCompleted(from, to, userService.getCurrentUser().get().getId(), true));
        output.setHoursWorked(repository.getHoursWorkedByCompleted(from, to, userService.getCurrentUser().get().getId(), true));
        return output;
    }

    public List<StatisticsOutput> eventsByTemplate(String startDate, String endDate) {
        List<StatisticsOutput> outputList = new ArrayList<>();
        Long userId = userService.getCurrentUser().get().getId();
        List<Long> templateIds = repository.getEventsFromTo(startDate, endDate, userId).stream().map(event -> event.getTemplate().getId()).distinct().collect(Collectors.toList());
        for(Long templateId : templateIds) {
            StatisticsOutput statisticsOutput = new StatisticsOutput();
            statisticsOutput.setTemplateTitle(templateService.getTemplate(templateId).getTitle());
            statisticsOutput.setEarned(repository.getEarnedByTemplate(startDate, endDate, userId, templateId));
            statisticsOutput.setDue(repository.getEarnedByTemplateAndPaid(startDate, endDate, userId, templateId, false));
            outputList.add(statisticsOutput);
        }
        return outputList;
    }

    public List<Event> getAllByPaging(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        Pageable paging;
        if (sort.equals(ESort.ASC)) {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }

        Page<Event> pagedResult = repository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    public Event getById(Long id) {
        return repository.findEventById(id);
    }

    public void deleteById(Long id) {
        repository.deleteEventById(id);
    }

}
