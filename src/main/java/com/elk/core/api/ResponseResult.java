package com.elk.core.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum  ResponseResult {

    CREATED("created"),
    UPDATED("updated");

    @Getter
    private String value;
}