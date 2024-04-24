package com.githubprocessor.repository;

import com.githubprocessor.model.GithubRepositoryData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GithubRepositoryDataRepository extends JpaRepository<GithubRepositoryData, String> {
}
