package com.example.jobbox.config.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class BadTokenException extends RuntimeException implements GraphQLError {

//    public BadTokenException() {
//    }
//
    public BadTokenException(String message) {
        super(message);
    }
    
//    @Override
//    public String getMessage() {
//        return "Token is invalid or expired";
//    }
    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }
}
