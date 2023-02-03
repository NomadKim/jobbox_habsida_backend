package com.example.jobbox.config.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
public class ProfileNotFoundExeption  extends RuntimeException implements GraphQLError {
    private final Long id;

    @Override
    public String getMessage() {
        return MessageFormat.format("Profile with ID ''{0}'' isn''t available", id);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }
}
