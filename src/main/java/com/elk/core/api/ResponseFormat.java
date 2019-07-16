package com.elk.core.api;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResponseFormat {

    JSON("json"),
    YAML("yaml");

    private String value;
}