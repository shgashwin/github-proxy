package com.informatica.github.feign.client;

import com.informatica.github.client.domain.RepositoryList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "getRepositories", url = "https://api.github.com/")
public interface GithubFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "search/repositories", produces = "application/json",
            params = {"q", "page"}, headers = "Accept: application/vnd.github.v3+json")
    RepositoryList getAllRepositories(@RequestParam final String q, @RequestParam final String page);
}