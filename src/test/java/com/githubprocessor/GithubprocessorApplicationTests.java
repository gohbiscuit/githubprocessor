package com.githubprocessor;

import com.githubprocessor.controller.GithubRepositoryController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GithubprocessorApplicationTests {

    @Autowired
    private GithubRepositoryController controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
