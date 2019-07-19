package com.elk.core.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ResponseFormat {

    JSON("json"),
    YAML("yaml");

    @Getter
    private String value;
}