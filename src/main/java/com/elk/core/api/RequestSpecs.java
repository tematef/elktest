package com.elk.core.api;

import com.elk.core.properties.PropertiesController;
import com.elk.core.util.UrlController;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Optional;

import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.config.RestAssuredConfig.config;

public class RequestSpecs {

    private static final String BASE_URI = UrlController.ELASTIC_URL;
    private static RequestSpecification requestSpecification;

    private RequestSpecs() {
    }

    public static RequestSpecification baseRequestSpec() {
        return Optional.ofNullable(requestSpecification).orElseGet(() -> requestSpecification = createBaseRequestSpec());
    }

    private static RequestSpecification createBaseRequestSpec() {
//        RestAssured.config = config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .addFilter(new ResponseLoggingFilter()) // Log responses to console
                .addFilter(new RequestLoggingFilter()) // Log requests to console
                .build();
    }
}