package com.example.jobbox.config.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class TimeExpiredException extends RuntimeException implements GraphQLError {
    @Override
    public String getMessage() {
        return "Time is expired";
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
