package com.elk.core.api;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

import java.util.Optional;

public class ResponseSpecs {

    private static ResponseSpecification responseJsonSpecification;
    private static ResponseSpecification responseYamlSpecification;

    private ResponseSpecs() { }

    public static ResponseSpecification jsonResponseSpec() {
        return Optional.ofNullable(responseJsonSpecification)
                .orElseGet(() -> responseJsonSpecification = new ResponseSpecBuilder()
                        .expectContentType(ContentType.JSON)
                        .build());
    }

    public static ResponseSpecification yamlResponseSpec() {
        return Optional.ofNullable(responseYamlSpecification)
                .orElseGet(() -> responseYamlSpecification = new ResponseSpecBuilder()
                        .expectContentType("application/yaml")
                        .build());
    }
}
