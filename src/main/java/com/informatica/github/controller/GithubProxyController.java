package com.informatica.github.controller;

import java.util.List;

import com.informatica.github.domain.Repository;
import com.informatica.github.service.GithubProxyService;
import com.informatica.github.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/search/repositories")
public class GithubProxyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GithubProxyController.class);

    @Autowired
    GithubProxyService githubProxyService;

    @GetMapping
    public List<Repository> getGitHubRepositories(@RequestParam final String q,
                                                  @RequestParam(required = false) final String page) {
        LOGGER.debug("GithubProxyController is called. q:{}, page:{} ", q, page);
        ValidationUtil.validateQueryParam(q);
        return githubProxyService.getRepositories(q, page);
    }
}
