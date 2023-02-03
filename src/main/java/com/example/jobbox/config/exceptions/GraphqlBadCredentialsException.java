package com.example.jobbox.config.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;

public class GraphqlBadCredentialsException extends BadCredentialsException implements GraphQLError {
    public GraphqlBadCredentialsException(String msg) {
        super(msg);
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
