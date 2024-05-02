package com.githubprocessor.repository;

import com.githubprocessor.model.GithubRepositoryData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GithubRepositoryDataRepositoryTests {

    @Mock
    private GithubRepositoryDataRepository repository;

    private GithubRepositoryData data;

    private String id;

    @BeforeEach
    public void setUp() throws Exception {
        String owner = "gohbiscuit";
        String repoName = "draw-canvas";
        id = owner + "/" + repoName;
        data = new GithubRepositoryData();
        data.setId(id);
        data.setFullName("gohbiscuit/draw-canvas");
        data.setDescription("A geometry assignment that uses factory pattern to draw primitive shapes via core Java. Built in maven.");
        data.setCloneUrl("https://github.com/gohbiscuit/draw-canvas.git");
        data.setStars(0);
    }


    @Test
    public void testFindById() {
        Date createdAt = new Date();
        data.setCreatedAt(createdAt);

        when(repository.findById(id)).thenReturn(Optional.of(data));

        Optional<GithubRepositoryData> result = repository.findById(id);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getFullName()).isEqualTo("gohbiscuit/draw-canvas");
        assertThat(result.get().getDescription()).isEqualTo("A geometry assignment that uses factory pattern to draw primitive shapes via core Java. Built in maven.");
        assertThat(result.get().getCloneUrl()).isEqualTo("https://github.com/gohbiscuit/draw-canvas.git");
        assertThat(result.get().getStars()).isEqualTo(0);
        assertThat(result.get().getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    public void testSave() {
        Date createdAt = new Date();
        data.setCreatedAt(createdAt);

        when(repository.save(any())).thenReturn(data);

        GithubRepositoryData savedData = repository.save(data);

        assertThat(savedData).isNotNull();
        assertThat(savedData.getId()).isEqualTo("gohbiscuit/draw-canvas");
        assertThat(savedData.getFullName()).isEqualTo("gohbiscuit/draw-canvas");
        assertThat(savedData.getDescription()).isEqualTo("A geometry assignment that uses factory pattern to draw primitive shapes via core Java. Built in maven.");
        assertThat(savedData.getCloneUrl()).isEqualTo("https://github.com/gohbiscuit/draw-canvas.git");
        assertThat(savedData.getStars()).isEqualTo(0);
        assertThat(savedData.getCreatedAt()).isEqualTo(createdAt);
    }
}
