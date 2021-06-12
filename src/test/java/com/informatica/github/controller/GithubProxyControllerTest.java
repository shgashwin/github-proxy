package com.informatica.github.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.informatica.github.domain.Repository;
import com.informatica.github.service.GithubProxyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GithubProxyController.class)
public class GithubProxyControllerTest {


    @MockBean
    private GithubProxyService githubProxyService;
    @Autowired
    private MockMvc mockMvc;
    private List<Repository> repositories;

    @BeforeEach
    public void setup() {
        repositories = new ArrayList<>();
        Repository repository = new Repository();
        repository.setName("Java");
        repository.setProjectId(1999);
        repository.setUrl("http://xyz.com");
        repository.setOwnerLogin(1234);
        repositories.add(repository);
    }

    @Test
    public void shouldGetRepositoriesWhenGithubServiceReturnRepositories() throws Exception {

        when(githubProxyService.getRepositories(any(), any())).thenReturn(repositories);

        this.mockMvc.perform(get("/rest/search/repositories").param("q", "topic:java"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Java")))
                .andExpect(content().string(containsString("1999")))
                .andExpect(content().string(containsString("http://xyz.com")));
    }

    @Test
    public void shouldThrowExceptionWhenQueryParameterContainsSpace() throws Exception {

        when(githubProxyService.getRepositories(any(), any())).thenReturn(repositories);

        this.mockMvc.perform(get("/rest/search/repositories").param("q", "topic: java"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("query parameters contains invalid string like space.")));
    }
}
