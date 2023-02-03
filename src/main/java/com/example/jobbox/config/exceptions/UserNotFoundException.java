package com.example.jobbox.config.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class UserNotFoundException extends RuntimeException implements GraphQLError {
    private final Long id;

    @Override
    public String getMessage() {
        return MessageFormat.format("User with ID ''{0}'' isn''t available", id);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }
    @Override
    public Map<String, Object> getExtensions() {
        return Collections.singletonMap("invalidField", id);
    }
}
