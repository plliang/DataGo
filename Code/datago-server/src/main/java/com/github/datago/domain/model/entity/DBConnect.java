package com.github.datago.domain.model.entity;

import com.github.datago.domain.DbType;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

@Getter
@Setter
public class DBConnect {

    private DbType dbType;

    private String url;

    private String username;

    private String password;

    private Properties properties;
}
