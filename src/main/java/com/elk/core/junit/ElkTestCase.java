package com.elk.core.junit;

import com.elk.apiclients.ApiClientManager;
import com.elk.apiclients.ElkNativeRestOperator;
import com.elk.apiclients.ElkOperator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class ElkTestCase {

    protected static ElkOperator elkOperator;
    protected static ElkNativeRestOperator restHighLevelClient;

    @BeforeAll
    public static void prepareApiClients() {
        elkOperator = ApiClientManager.getElkOperator();
        restHighLevelClient = ApiClientManager.getElkNativeRestOperator();
    }

    @AfterAll
    public static void closeClient() {
        restHighLevelClient.close();
    }
}