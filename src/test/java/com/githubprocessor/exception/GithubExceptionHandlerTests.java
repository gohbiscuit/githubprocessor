package com.githubprocessor.exception;

import com.githubprocessor.controller.GithubRepositoryController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class GithubExceptionHandlerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GithubRepositoryController controller;

    @Test
    public void testWhenRepoIsInvalidHandleGithubException() throws Exception {
        String owner = "gohbiscuit";
        String repo = "hello-world";
        GithubException exception = new GithubException(GithubErrorCodes.REPO_DETAILS_NOT_FOUND, String.format(GithubErrorCodes.REPO_DETAILS_NOT_FOUND.getDescription(), "https://api.github.com/repos/gohbiscuit/hello-world"));
        Mockito.when(controller.getRepositoryDetails(owner, repo)).thenThrow(exception);
        mockMvc.perform(get("/repositories/gohbiscuit/hello-world"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is("NOT_FOUND")))
                .andExpect(jsonPath("$.title", is("REPOSITORY-NOT-FOUND")))
                .andExpect(jsonPath("$.message", is("Details not found for https://api.github.com/repos/gohbiscuit/hello-world")));

    }

    @Test
    public void testWhenOwnerIsInvalidHandleGithubException() throws Exception {
        String owner = "invalid-owner";
        String repo = "draw-canvas";
        GithubException exception = new GithubException(GithubErrorCodes.REPO_DETAILS_NOT_FOUND, String.format(GithubErrorCodes.REPO_DETAILS_NOT_FOUND.getDescription(), "https://api.github.com/repos/invalid-owner/draw-canvas"));
        Mockito.when(controller.getRepositoryDetails(owner, repo)).thenThrow(exception);
        mockMvc.perform(get("/repositories/invalid-owner/draw-canvas"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is("NOT_FOUND")))
                .andExpect(jsonPath("$.title", is("REPOSITORY-NOT-FOUND")))
                .andExpect(jsonPath("$.message", is("Details not found for https://api.github.com/repos/invalid-owner/draw-canvas")));

    }

    @Test
    public void testWhenUrlIsInvalidHandleGeneralException() throws Exception {
        String owner = "12313123";
        Mockito.when(controller.getRepositoryDetails(owner, null)).thenThrow(new RuntimeException("No static resource repositories/12313123."));
        mockMvc.perform(get("/repositories/12313123"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code", is("INTERNAL_SERVER_ERROR")))
                .andExpect(jsonPath("$.title", is("INTERNAL-SERVER-ERROR")))
                .andExpect(jsonPath("$.message", is("No static resource repositories/12313123.")));

    }
}