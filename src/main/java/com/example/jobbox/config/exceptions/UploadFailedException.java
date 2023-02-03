package com.example.jobbox.config.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UploadFailedException extends RuntimeException implements GraphQLError {
    private final String file;

    public UploadFailedException(String file, Throwable cause) {
        super(cause);
        this.file = file;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format("Unable to upload file ''{0}''", file);
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
        return Collections.singletonMap("invalidField", file);
    }
}
