package com.github.datago.domain.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

@Getter
@Setter
public class DBConnect {

    private String url;

    private String username;

    private String password;

    private Properties properties;
}
