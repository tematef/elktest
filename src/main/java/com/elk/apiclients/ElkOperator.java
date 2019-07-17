package com.elk.apiclients;

import com.elk.core.api.RequestSpecs;
import com.elk.core.api.ResponseSpecs;
import com.elk.dataobjects.elk.api.PostDataElkResponse;
import com.elk.dataobjects.elk.api.SearchElkResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class ElkOperator {

    public PostDataElkResponse postDocument(String indexName, String indexType, Object body) {
        return postDocumentSourceCall(indexName, indexType, body).as(PostDataElkResponse.class);
    }

    private Response postDocumentSourceCall(String indexName, String indexType, Object body) {
        return given().spec(RequestSpecs.baseRequestSpec())
                .when().body(body).post(String.format("/%s/%s", indexName, indexType))
                .then().assertThat().spec(ResponseSpecs.baseResponseSpec()).statusCode(HttpStatus.SC_CREATED)
                .extract().response();
    }

    public SearchElkResponse searchDocument(String indexName, String field, String value) {
        return searchDocumentSourceCall(indexName, field, value).as(SearchElkResponse.class);
    }

    private Response searchDocumentSourceCall(String indexName, String field, String value) {
        return given().spec(RequestSpecs.baseRequestSpec())
                .when().get(String.format("/%s/_search?q=%s:%s", indexName, field, value))
                .then().assertThat().spec(ResponseSpecs.baseResponseSpec()).statusCode(HttpStatus.SC_CREATED)
                .extract().response();
    }
}