package com.githubprocessor.controller;

import com.githubprocessor.dto.GithubRepositoryDto;
import com.githubprocessor.exception.GithubException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class GithubRepositoryControllerIntegrationTests {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getRepositoryDetailsReturnsRepositoryInfo() {
        ResponseEntity<GithubRepositoryDto> response = restTemplate.getForEntity("http://localhost:" + port + "/repositories/divaibhav/helloworld", GithubRepositoryDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("divaibhav/helloworld", response.getBody().getFullName());
        assertEquals("Sun Dec 09 16:17:56 SGT 2018", response.getBody().getCreatedAt().toString());
        assertEquals("A sample hello world project", response.getBody().getDescription());
        assertEquals("https://github.com/divaibhav/helloworld.git", response.getBody().getCloneUrl());
        assertEquals(0, response.getBody().getStars());
    }

    @Test
    public void givenInvalidRepositoryReturns404NotFound() throws GithubException {
        ResponseEntity<GithubRepositoryDto> response = restTemplate.getForEntity("http://localhost:" + port + "/repositories/divaibhav/helloworld404", GithubRepositoryDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        exception.expect(GithubException.class);
        exception.expectMessage("Details not found for https://api.github.com/repos/divaibhav/helloworld404");
    }

    @Test
    public void givenInvalidOwnerNameReturns404NotFound() {
        ResponseEntity<GithubRepositoryDto> response = restTemplate.getForEntity("http://localhost:" + port + "/repositories/invalid-owner/helloworld", GithubRepositoryDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        exception.expect(GithubException.class);
        exception.expectMessage("Details not found for https://api.github.com/repos/invalid-owner/helloworld");
    }

    @Test
    public void givenInvalidUrlPathReturns500InternalServerError() {
        ResponseEntity<GithubRepositoryDto> response = restTemplate.getForEntity("http://localhost:" + port + "/repositories/invalid-owner", GithubRepositoryDto.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        exception.expect(GithubException.class);
        exception.expectMessage("No static resource repositories/invalid-owner.");

        response = restTemplate.getForEntity("http://localhost:" + port + "/repositories/invalid-owner/1/2/3/4/5", GithubRepositoryDto.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        exception.expect(GithubException.class);
        exception.expectMessage("No static resource repositories/invalid-owner/1/2/3/4/5.");
    }
}
