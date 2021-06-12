package com.informatica.github.service;

import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.List;

import com.informatica.github.client.domain.RepositoryList;
import com.informatica.github.domain.Repository;
import com.informatica.github.exception.GithubProxyException;
import com.informatica.github.feign.client.GithubFeignClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GithubProxyService {

    @Autowired
    GithubFeignClient gitHubClient;

    public List<Repository> getRepositories(final String q, final String page) {

        RepositoryList allRepositories;

        try {
            allRepositories = gitHubClient.getAllRepositories(q, page);
        } catch (final FeignException.UnprocessableEntity exception) { // TODO should catch specific exception
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
