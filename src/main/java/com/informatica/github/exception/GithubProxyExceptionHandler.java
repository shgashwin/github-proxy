package com.informatica.github.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GithubProxyExceptionHandler
        extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GithubProxyExceptionHandler.class);

    @ExceptionHandler(value = {InvalidParametersException.class})
    protected ResponseEntity<Object> handleInvalidParameters(final RuntimeException ex, final WebRequest request) {
        LOGGER.warn("Exception occurred!! ex: {}", ex.getMessage());
        return handleExceptionInternal(ex, new Message(ex.getMessage(), "Please enter query parameters without space."), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {GithubProxyException.class})
    protected ResponseEntity<Object> handleServiceUnavailable(final RuntimeException ex, final WebRequest request) {
        LOGGER.warn("Exception occurred!! ex: {}", ex);
        return handleExceptionInternal(ex, new Message(ex.getMessage(), "Exception occurred while getting repository list."), new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
    }
}