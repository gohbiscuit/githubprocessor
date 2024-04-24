package com.githubprocessor.mapper;

import com.githubprocessor.dto.GithubRepositoryDto;
import com.githubprocessor.model.GithubRepositoryData;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GithubRepositoryMapperTests {

    @Test
    public void testToDto() {
        GithubRepositoryData data = new GithubRepositoryData();
        data.setDescription("A sample GitHub repository");
        data.setFullName("octocat/Hello-World");
        data.setStars(42);
        data.setCloneUrl("https://github.com/octocat/Hello-World.git");
        data.setCreatedAt(new Date());

        GithubRepositoryMapper mapper = new GithubRepositoryMapper();

        GithubRepositoryDto dto = mapper.toDto(data);

        assertEquals(data.getDescription(), dto.getDescription(), "Descriptions should match");
        assertEquals(data.getFullName(), dto.getFullName(), "Full names should match");
        assertEquals(data.getStars(), dto.getStars(), "Stars should match");
        assertEquals(data.getCloneUrl(), dto.getCloneUrl(), "Clone URLs should match");
        assertEquals(data.getCreatedAt(), dto.getCreatedAt(), "Creation dates should match");
    }
}
