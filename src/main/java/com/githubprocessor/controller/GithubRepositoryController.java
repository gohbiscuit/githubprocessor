package com.githubprocessor.controller;

import com.githubprocessor.dto.GithubRepositoryDto;
import com.githubprocessor.exception.GithubException;
import com.githubprocessor.service.GithubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.githubprocessor.exception.GithubExceptionHandler.getErrorResponseEntity;

@RestController
@RequestMapping("/repositories")
@Slf4j
public class GithubRepositoryController {

    @Autowired
    private GithubService gitHubService;

    @GetMapping("/{owner}/{repositoryName}")
    public ResponseEntity<?> getRepositoryDetails(@PathVariable String owner, @PathVariable String repositoryName) throws Exception {
        try {
            GithubRepositoryDto response = gitHubService.getRepositoryDetails(owner, repositoryName);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (GithubException e) {
            return getErrorResponseEntity(e);
        }
    }
}