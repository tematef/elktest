package com.elk.apiclients;

import java.util.Optional;

public class ApiClientManager {

    private static ElkOperator elkOperator;
    private static ElkNativeRestOperator elkNativeRestOperator;

    private ApiClientManager() {
    }

    public static ElkOperator getElkOperator() {
        return Optional.ofNullable(elkOperator)
                .orElseGet(() -> elkOperator = new ElkOperator());
    }

    public static ElkNativeRestOperator getElkNativeRestOperator() {
        return Optional.ofNullable(elkNativeRestOperator)
                .orElseGet(() -> elkNativeRestOperator = new ElkNativeRestOperator());
    }
}