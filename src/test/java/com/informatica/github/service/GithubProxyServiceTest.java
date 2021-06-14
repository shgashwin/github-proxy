package com.informatica.github.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.informatica.github.client.domain.Item;
import com.informatica.github.client.domain.Owner;
import com.informatica.github.client.domain.RepositoryList;
import com.informatica.github.domain.Repository;
import com.informatica.github.exception.GithubProxyException;
import com.informatica.github.feign.client.GithubFeignClient;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GithubProxyServiceTest {

    @InjectMocks
    private GithubProxyService githubProxyService;
    @Mock
    private GithubFeignClient githubFeignClient;
    private RepositoryList repositories;

    @BeforeEach
    public void setup() {
        repositories = new RepositoryList();
        final List<Item> items = new ArrayList<>();
        final Item item = new Item();
        item.setName("name");
        item.setId(1234);
        item.setUrl("http://xyz.com");
        final Owner owner = new Owner();
        owner.setId(9999);
        item.setOwner(owner);
        items.add(item);
        repositories.setItems(items);
    }

    @Test
    public void shouldGetRepositoriesWhenGitHubReturnsRepositories() {
        when(githubFeignClient.getAllRepositories(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(repositories);
        final List<Repository> repositories = githubProxyService.getRepositories("topic:java", "1");
        assertThat(repositories.size(), is(1));
        assertThat(repositories.get(0).getName(), is("name"));
        assertThat(repositories.get(0).getUrl(), is("http://xyz.com"));
        assertThat(repositories.get(0).getProjectId(), is(1234L));
        assertThat(repositories.get(0).getOwnerLogin(), is(9999));
    }


    @Test
    public void shouldThrowGithubProxyExceptionWhenGithubThrowsFeignException() {
        when(githubFeignClient.getAllRepositories(ArgumentMatchers.any(), ArgumentMatchers.any())).thenThrow(FeignException.UnprocessableEntity.class);
        Assertions.assertThrows(GithubProxyException.class, () ->
                githubProxyService.getRepositories("topic:java", "1")
        );
    }

}
