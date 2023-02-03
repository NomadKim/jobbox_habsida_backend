package com.example.jobbox.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.jobbox.model.User;
import com.example.jobbox.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserResolver implements GraphQLResolver<User> {
    private final UserService service;

    @PreAuthorize("isAuthenticated()")
    public String getToken(User user) {
        return service.getToken(user);
    }
}
