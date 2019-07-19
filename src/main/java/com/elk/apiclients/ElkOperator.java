package com.elk.apiclients;

import com.elk.core.api.RequestSpecs;
import com.elk.core.api.ResponseFormat;
import com.elk.core.api.ResponseSpecs;
import com.elk.core.util.YamlMapper;
import com.elk.dataobjects.elk.IndexElk;
import com.elk.dataobjects.elk.api.DocumentDataElkResponse;
import com.elk.dataobjects.elk.api.SearchElkResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class ElkOperator {

    private YamlMapper yamlMapper;

    ElkOperator() {
        yamlMapper = new YamlMapper();
    }

    @Step("post document to {0} index with {1} return type and body {2}")
    public DocumentDataElkResponse postDocument(IndexElk index, Object body, ResponseFormat responseFormat) {
        switch (responseFormat) {
            case JSON:
                return postDocumentSourceCall(index, body, responseFormat)
                        .then().assertThat()
                        .spec(ResponseSpecs.jsonResponseSpec())
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract().response().as(DocumentDataElkResponse.class);
            case YAML:
                String sourceYaml = postDocumentSourceCall(index, body, responseFormat)
                        .then().assertThat()
                        .spec(ResponseSpecs.yamlResponseSpec())
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract().response().getBody().prettyPrint();
                return yamlMapper.map(sourceYaml, DocumentDataElkResponse.class);

        }
        throw new IllegalArgumentException(String.format("ResponseFormat miss match expected: %s", responseFormat.getValue()));
    }

    private Response postDocumentSourceCall(IndexElk index, Object body, ResponseFormat responseFormat) {
        return given().spec(RequestSpecs.baseRequestSpec())
                .when().body(body)
                .post(String.format("/%s/%s?format=%s&refresh=true",
                        index.getIndexName(), index.getIndexType(), responseFormat.getValue()));
    }

    @Step("udate document in {0} index by {1} doc id with {2} body and return type {3}")
    public DocumentDataElkResponse updateDocument(IndexElk index, String documentID, Object body, ResponseFormat responseFormat) {
        switch (responseFormat) {
            case JSON:
                return updateDocumentSourceCall(index, documentID, body, responseFormat)
                        .then().assertThat()
                        .spec(ResponseSpecs.jsonResponseSpec())
                        .statusCode(HttpStatus.SC_OK)
                        .extract().response().as(DocumentDataElkResponse.class);
            case YAML:
                String sourceYaml = updateDocumentSourceCall(index, documentID, body, responseFormat)
                        .then().assertThat()
                        .spec(ResponseSpecs.yamlResponseSpec())
                        .statusCode(HttpStatus.SC_OK)
                        .extract().response().getBody().prettyPrint();
                return yamlMapper.map(sourceYaml, DocumentDataElkResponse.class);

        }
        throw new IllegalArgumentException(String.format("ResponseFormat miss match expected: %s", responseFormat.getValue()));
    }

    private Response updateDocumentSourceCall(IndexElk index, String documentID, Object body, ResponseFormat responseFormat) {
        return given().spec(RequestSpecs.baseRequestSpec())
                .when().body(body)
                .put(String.format("/%s/_doc/%s?format=%s&refresh=true",
                        index.getIndexName(), documentID, responseFormat.getValue()));
    }

    @Step("search document in {0} index by {1} field with {2} value and return type")
    public SearchElkResponse searchDocument(String indexName, String field, String value, ResponseFormat responseFormat) {
        switch (responseFormat) {
            case JSON:
                return searchDocumentSourceCall(indexName, field, value, responseFormat)
                        .then().assertThat()
                        .spec(ResponseSpecs.jsonResponseSpec())
                        .statusCode(HttpStatus.SC_OK)
                        .extract().response().as(SearchElkResponse.class);
            case YAML:
                String sourceYaml = searchDocumentSourceCall(indexName, field, value, responseFormat)
                        .then().assertThat()
                        .spec(ResponseSpecs.yamlResponseSpec())
                        .statusCode(HttpStatus.SC_OK)
                        .extract().response().getBody().prettyPrint();
                return yamlMapper.map(sourceYaml, SearchElkResponse.class);

        }
        throw new IllegalArgumentException(String.format("ResponseFormat miss match expected: %s", responseFormat.getValue()));
    }

    private Response searchDocumentSourceCall(String indexName, String field, String value, ResponseFormat responseFormat) {
        String searchBody = "{\"query\":{\"match\":{\"%s\":\"%s\"}}}";
        return given().spec(RequestSpecs.baseRequestSpec())
                .when().body(String.format(searchBody, field, value))
                .get(String.format("/%s/_search?format=%s", indexName, responseFormat.getValue()));
    }
}