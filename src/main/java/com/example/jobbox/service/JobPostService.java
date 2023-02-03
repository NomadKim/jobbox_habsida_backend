package com.example.jobbox.service;

import com.example.jobbox.config.util.EventUtil;
import com.example.jobbox.inputModels.CreateJobPostInput;
import com.example.jobbox.inputModels.UpdateJobPostInput;
import com.example.jobbox.model.JobPost;
import com.example.jobbox.model.User;
import com.example.jobbox.model.enums.ESort;
import com.example.jobbox.repository.JobPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class JobPostService {
    private final JobPostRepository jobPostRepository;
    private final UserService userService;


    @Transactional
    public JobPost createJobPost(CreateJobPostInput createJobPostInput){
        User user = userService.getCurrentUser().orElseThrow();
        JobPost jobPost = new JobPost();
        jobPost.setDate(createJobPostInput.getDate());
        jobPost.setTitle(createJobPostInput.getTitle());
        jobPost.setAddress(createJobPostInput.getAddress());
        jobPost.setPosition(createJobPostInput.getPosition());
        jobPost.setWorkTimeFrom(createJobPostInput.getWorkTimeFrom());
        jobPost.setWorkTimeTo(createJobPostInput.getWorkTimeTo());
        jobPost.setPayment(createJobPostInput.getPayment());
        jobPost.setOvertimePay(createJobPostInput.getOvertimePay());
        jobPost.setOvertimeFrom(createJobPostInput.getOvertimeFrom());
        jobPost.setOvertimeTo(createJobPostInput.getOvertimeTo());
        jobPost.setDailySum(receiveDailySum(createJobPostInput.getIsPaidHourly(),
                createJobPostInput.getIsOvertimePaidHourly(),
                createJobPostInput.getWorkTimeFrom(),
                createJobPostInput.getWorkTimeTo(),
                createJobPostInput.getOvertimeFrom(),
                createJobPostInput.getOvertimeTo(),
                createJobPostInput.getPayment(),
                createJobPostInput.getOvertimePay()));
        jobPost.setHoursWorked(EventUtil.durationInMinutes(createJobPostInput.getWorkTimeFrom(),
                createJobPostInput.getWorkTimeTo(),
                createJobPostInput.getOvertimeFrom(),
                createJobPostInput.getOvertimeTo()));
        jobPost.setIsPaidHourly(createJobPostInput.getIsPaidHourly());
        jobPost.setIsOvertimePaidHourly(createJobPostInput.getIsOvertimePaidHourly());
        jobPost.setCreatedAt(LocalDateTime.now());
        jobPost.setIsPositionOpen(createJobPostInput.getIsPositionOpen());
        jobPost.setTotalRecruits(createJobPostInput.getTotalRecruits());
        jobPost.setApplicationsReceived(0);
        jobPost.setPostedUser(user);
        jobPost.setApplicants(new HashSet<>());
        jobPost.setUsersEmployed(new HashSet<>());
        jobPost.setGender(createJobPostInput.getGender());
        jobPost.setVisa(createJobPostInput.getVisa());
        jobPost.setAge(createJobPostInput.getAge());
        jobPost.setInvited(userService.getUsersByPhoneNumber(createJobPostInput.getInvitedUsersByPhoneNumber()));
        return jobPostRepository.saveAndFlush(jobPost);
    }

    public void deleteJobPostById(Long id){
        jobPostRepository.deleteById(id);
    }


    @Transactional
    public JobPost getById(Long id) {
        return jobPostRepository.findById(id).orElseThrow();
    }

    @Transactional
    public JobPost updateJobPost(UpdateJobPostInput updateJobPostInput) throws NoSuchElementException{
        JobPost jobPost = jobPostRepository.findById(updateJobPostInput.getId()).orElseThrow();
        jobPost.setDate(updateJobPostInput.getDate());
        jobPost.setTitle(updateJobPostInput.getTitle());
        jobPost.setAddress(updateJobPostInput.getAddress());
        jobPost.setPosition(updateJobPostInput.getPosition());
        jobPost.setWorkTimeFrom(updateJobPostInput.getWorkTimeFrom());
        jobPost.setWorkTimeTo(updateJobPostInput.getWorkTimeTo());
        jobPost.setPayment(updateJobPostInput.getPayment());
        jobPost.setOvertimePay(updateJobPostInput.getOvertimePay());
        jobPost.setOvertimeFrom(updateJobPostInput.getOvertimeFrom());
        jobPost.setOvertimeTo(updateJobPostInput.getOvertimeTo());
        jobPost.setDailySum(receiveDailySum(updateJobPostInput.getIsPaidHourly(),
                updateJobPostInput.getIsOvertimePaidHourly(),
                updateJobPostInput.getWorkTimeFrom(),
                updateJobPostInput.getWorkTimeTo(),
                updateJobPostInput.getOvertimeFrom(),
                updateJobPostInput.getOvertimeTo(),
                updateJobPostInput.getPayment(),
                updateJobPostInput.getOvertimePay()));
        jobPost.setHoursWorked(EventUtil.durationInMinutes(updateJobPostInput.getWorkTimeFrom(),
                updateJobPostInput.getWorkTimeTo(),
                updateJobPostInput.getOvertimeFrom(),
                updateJobPostInput.getOvertimeTo()));
        jobPost.setIsPaidHourly(updateJobPostInput.getIsPaidHourly());
        jobPost.setIsOvertimePaidHourly(updateJobPostInput.getIsOvertimePaidHourly());
        jobPost.setUpdatedAt(LocalDateTime.now());
        jobPost.setTotalRecruits(updateJobPostInput.getTotalRecruits());
        jobPost.setIsPositionOpen(updateJobPostInput.getIsPositionOpen());
        jobPost.setGender(updateJobPostInput.getGender());
        jobPost.setVisa(updateJobPostInput.getVisa());
        jobPost.setAge(updateJobPostInput.getAge());
        jobPost.setInvited(userService.getUsersByPhoneNumber(updateJobPostInput.getInvitedUsersByPhoneNumber()));
        return jobPostRepository.saveAndFlush(jobPost);
    }

    @Transactional
    public List<JobPost> recieveAllJobPost(Integer pageNo, Integer pageSize, String sortBy, ESort sort){
        Pageable paging;
        if (sort.equals(ESort.ASC)){
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }

        Page<JobPost> pagedResult = jobPostRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }



    private Float receiveDailySum(boolean isPaidHourly, boolean isOvertimePaidHourly, String from, String to,
                                  String overtimeFrom, String overtimeTo, Float pay, Float overpay){
        Float dailypay = pay * 100;
        Float overTimePay = overpay * 100;
        return EventUtil.dailySumCount(isPaidHourly,
                isOvertimePaidHourly,
                from,
                to,
                overtimeFrom,
                overtimeTo,
                Integer.valueOf(dailypay.intValue()),
                Integer.valueOf(overTimePay.intValue())) / 100;
    }
}
