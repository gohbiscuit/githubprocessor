package com.githubprocessor.mapper;

import com.githubprocessor.dto.GithubRepositoryDto;
import com.githubprocessor.model.GithubRepositoryData;
import org.springframework.stereotype.Component;

@Component
public class GithubRepositoryMapper {
    public GithubRepositoryDto toDto(GithubRepositoryData data) {
        GithubRepositoryDto dto = new GithubRepositoryDto();
        dto.setDescription(data.getDescription());
        dto.setFullName(data.getFullName());
        dto.setStars(data.getStars());
        dto.setCloneUrl(data.getCloneUrl());
        dto.setCreatedAt(data.getCreatedAt());
        return dto;
    }
}