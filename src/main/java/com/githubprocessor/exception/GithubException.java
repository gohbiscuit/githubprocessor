package com.githubprocessor.exception;

import lombok.Getter;

@Getter
public class GithubException extends RuntimeException {

    private GithubErrorCodes errorCodes;

    public GithubException(GithubErrorCodes errorCodes, String message) {
        super(message);
        this.errorCodes = errorCodes;
    }
}
