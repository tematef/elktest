package com.elk.core.api;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

import java.util.Optional;

public class ResponseSpecs {

    private static ResponseSpecification responseSpecification;

    private ResponseSpecs() { }

    public static ResponseSpecification baseResponseSpec() {
        return Optional.ofNullable(responseSpecification).orElseGet(() -> responseSpecification = createBaseResponseSpec());
    }

    private static ResponseSpecification createBaseResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }
}
