package com.githubprocessor.dto;


import lombok.Data;

import java.util.Date;

@Data
public class GithubRepositoryDto {
    private String fullName;

    private String description;

    private String cloneUrl;

    private int stars;

    private Date createdAt;
}
