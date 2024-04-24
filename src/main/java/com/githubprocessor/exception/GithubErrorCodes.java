package com.githubprocessor.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GithubErrorCodes {
    REPO_DETAILS_NOT_FOUND(HttpStatus.NOT_FOUND, "REPOSITORY-NOT-FOUND", "Details not found for %s"),
    REPO_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "REPOSITORY-SERVER-ERROR", "Error occurred while fetching data for %s");

    private final HttpStatus code;
    private final String title;
    private final String description;

    GithubErrorCodes(HttpStatus code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
    }
}
