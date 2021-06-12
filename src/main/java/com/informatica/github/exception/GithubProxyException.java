package com.informatica.github.exception;

public class GithubProxyException extends RuntimeException{
    public GithubProxyException(final String message, final Exception ex) {
        super(message,ex);
    }
}
