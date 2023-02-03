package com.example.jobbox.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.jobbox.model.JobPost;
import com.example.jobbox.model.User;
import com.example.jobbox.repository.UserRepository;
import com.example.jobbox.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostResolver implements GraphQLResolver<JobPost> {
//
//    private final UserService userService;
//    private final UserRepository userRepository;
//
//    public User getPostedUser(JobPost jobPost){
//        return userService.getUser(jobPost.getPostedUser().getId());
//    }

}
