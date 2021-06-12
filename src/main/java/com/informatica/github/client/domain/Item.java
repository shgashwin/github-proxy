package com.informatica.github.client.domain;

import lombok.Data;

@Data
public class Item {
    public int id;
    public String name;
    public Owner owner;
    public String url;
}
