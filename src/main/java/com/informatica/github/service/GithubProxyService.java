package com.informatica.github.service;

import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.List;

import com.informatica.github.client.domain.RepositoryList;
import com.informatica.github.domain.Repository;
import com.informatica.github.exception.GithubProxyException;
import com.informatica.github.feign.client.GithubFeignClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GithubProxyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GithubProxyService.class);

    @Autowired
    GithubFeignClient gitHubClient;

    public List<Repository> getRepositories(final String q, final String page) {

        RepositoryList allRepositories;

        try {
            allRepositories = gitHubClient.getAllRepositories(q, page);
        } catch (final FeignException.UnprocessableEntity exception) {
            LOGGER.warn("Exception occurred while calling the get repositories q:{} page:{}", q, page);
            throw new GithubProxyException("Exception occurred while getting repository list.", exception);
        }

        final List<Repository> repositories = new ArrayList<>();
        ofNullable(allRepositories).ifPresent(repositoryList ->
                repositoryList.getItems().stream().forEach(project -> {
                    Repository repository = new Repository();
                    repository.setProjectId(project.getId());
                    repository.setName(project.getName());
                    repository.setUrl(project.getUrl());
                    repository.setOwnerLogin(project.getOwner().getId());
                    repositories.add(repository);
                })
        );

        return repositories;
    }
}
