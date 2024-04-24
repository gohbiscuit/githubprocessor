package com.githubprocessor.service;

import com.githubprocessor.dto.GithubRepositoryDto;
import com.githubprocessor.model.GithubRepositoryData;
import com.githubprocessor.repository.GithubRepositoryDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GithubServiceTests {
    @Autowired
    private GithubService githubService;

    @MockBean
    private GithubRepositoryDataRepository repository;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void testRepositoryDetailsFetched() {
        String owner = "gohbiscuit";
        String repoName = "draw-canvas";
        GithubRepositoryData detail = new GithubRepositoryData();
        detail.setId(owner + "/" + repoName);
        detail.setFullName("gohbiscuit/draw-canvas");
        detail.setDescription("A geometry assignment that uses factory pattern to draw primitive shapes via core Java. Built in maven.");
        detail.setCloneUrl("https://github.com/gohbiscuit/draw-canvas.git");
        detail.setStars(0);
        Date createdAt = new Date();
        detail.setCreatedAt(createdAt);

        when(repository.findById(anyString())).thenReturn(Optional.of(detail));

        GithubRepositoryDto responseDto = githubService.getRepositoryDetails(owner, repoName);
        assertEquals("gohbiscuit/draw-canvas", responseDto.getFullName());
        assertEquals("A geometry assignment that uses factory pattern to draw primitive shapes via core Java. Built in maven.", responseDto.getDescription());
        assertEquals("https://github.com/gohbiscuit/draw-canvas.git", responseDto.getCloneUrl());
        assertEquals(0, responseDto.getStars());
        assertEquals(createdAt, responseDto.getCreatedAt());
    }
}
