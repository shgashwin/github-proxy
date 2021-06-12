package com.informatica.github.domain;

import lombok.Data;

@Data
public class Repository {
    private long projectId;
    private String name;
    private String url;
    private int ownerLogin;

}
