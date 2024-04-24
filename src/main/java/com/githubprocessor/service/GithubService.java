package com.githubprocessor.service;

import com.githubprocessor.dto.GithubRepositoryDto;
import com.githubprocessor.exception.GithubErrorCodes;
import com.githubprocessor.exception.GithubException;
import com.githubprocessor.mapper.GithubRepositoryMapper;
import com.githubprocessor.model.GithubRepositoryData;
import com.githubprocessor.repository.GithubRepositoryDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GithubService {
    private static final String GITHUB_API = "https://api.github.com/repos/";
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private GithubRepositoryDataRepository repository;

    @Autowired
    private GithubRepositoryMapper mapper;

    public GithubRepositoryDto getRepositoryDetails(String owner, String repoName) throws GithubException {
        String id = owner + "/" + repoName;
        String url = GITHUB_API + id;
        GithubRepositoryData data = repository.findById(id)
                .orElseGet(() -> {
                    try {
                        log.info("fetchAndCacheRepositoryDetails:: id: {}, url: {}", id, url);
                        return fetchAndCacheRepositoryDetails(id, url);
                    } catch (GithubException ghe) {
                        log.error("getRepositoryDetails GithubException :: " + ghe.getMessage());
                        throw ghe;
                    } catch (Exception e) {
                        log.error("getRepositoryDetails Exception :: " + e.getMessage());
                        throw new GithubException(GithubErrorCodes.REPO_SERVER_ERROR, e.getMessage());
                    }
                });
        return mapper.toDto(data);
    }

    private GithubRepositoryData fetchAndCacheRepositoryDetails(String id, String url) throws GithubException {
        try {
            ResponseEntity<GithubRepositoryData> response = restTemplate.getForEntity(url, GithubRepositoryData.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                GithubRepositoryData data = response.getBody();
                data.setId(id);
                log.info("fetchAndCacheRepositoryDetails GithubRepositoryData found. Caching record: " + data);
                repository.save(data);
                return data;
            } else {
                log.error("fetchAndCacheRepositoryDetails REPO_DETAILS_NOT_FOUND:: ");
                throw new GithubException(GithubErrorCodes.REPO_DETAILS_NOT_FOUND, String.format(GithubErrorCodes.REPO_DETAILS_NOT_FOUND.getDescription(), url));
            }
        } catch (RestClientException e) {
            log.error("fetchAndCacheRepositoryDetails REPO_DETAILS_NOT_FOUND:: ");
            throw new GithubException(GithubErrorCodes.REPO_DETAILS_NOT_FOUND, String.format(GithubErrorCodes.REPO_DETAILS_NOT_FOUND.getDescription(), url));
        }
    }
}
