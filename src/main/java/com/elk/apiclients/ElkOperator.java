package com.elk.apiclients;

import com.elk.core.api.RequestSpecs;
import com.elk.core.api.ResponseSpecs;
import com.elk.dataobjects.elk.api.CreateIndexElkResponse;
import com.elk.dataobjects.elk.api.DeleteIndexElkResponse;
import com.elk.dataobjects.elk.api.GetIndicesElkResponse;
import com.elk.dataobjects.elk.IndexSettingsElk;
import com.elk.dataobjects.elk.api.PostDataElkResponse;
import com.elk.dataobjects.elk.api.SearchElkResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class ElkOperator {

    private static final String POST_DATA = "/%s/%s";
    private static final String GET_INDICES = "/_cat/indices?v&format=json";
    private static final String GET_DATA_FROM_INDEX = "/%s/_search?q=*:*";
    private static final String PUT_INDEX_ENDPOINT = "/%s";

    public CreateIndexElkResponse putIndex(String indexName, IndexSettingsElk settings) {
        return putIndexSourceCall(indexName, settings).as(CreateIndexElkResponse.class);
    }

    private Response putIndexSourceCall(String indexName, IndexSettingsElk settings) {
        return given().spec(RequestSpecs.baseRequestSpec())
                .when().body(settings).put(String.format(PUT_INDEX_ENDPOINT, indexName))
                .then().assertThat().spec(ResponseSpecs.baseResponseSpec()).statusCode(HttpStatus.SC_OK)
                .extract().response();
    }

    public GetIndicesElkResponse getIndices() {
        return new GetIndicesElkResponse(getIndicesSourceCall().body().jsonPath()
                .getList(".", GetIndicesElkResponse.SingleIndexElkResponce.class));
    }

    private Response getIndicesSourceCall() {
        return given().spec(RequestSpecs.baseRequestSpec())
                .when().get(GET_INDICES)
                .then().assertThat().spec(ResponseSpecs.baseResponseSpec()).statusCode(HttpStatus.SC_OK)
                .extract().response();
    }

    public DeleteIndexElkResponse deleteIndex(String indexName) {
        return deleteIndexSourceCall(indexName).as(DeleteIndexElkResponse.class);
    }

    private Response deleteIndexSourceCall(String indexName) {
        return given().spec(RequestSpecs.baseRequestSpec())
                .when().delete("/".concat(indexName))
                .then().assertThat().spec(ResponseSpecs.baseResponseSpec()).statusCode(HttpStatus.SC_OK)
                .extract().response();
    }

    public PostDataElkResponse postDocument(String indexName, String indexType, Object body) {
        return postDocumentSourceCall(indexName, indexType, body).as(PostDataElkResponse.class);
    }

    private Response postDocumentSourceCall(String indexName, String indexType, Object body) {
        return given().spec(RequestSpecs.baseRequestSpec())
                .when().body(body).post(String.format(POST_DATA, indexName, indexType))
                .then().assertThat().spec(ResponseSpecs.baseResponseSpec()).statusCode(HttpStatus.SC_CREATED)
                .extract().response();
    }

    public SearchElkResponse getAllDocuments(String indexName) {
        return getAllDocumentsSouceCall(indexName).as(SearchElkResponse.class);
    }

    public Response getAllDocumentsSouceCall(String indexName) {
        return given().spec(RequestSpecs.baseRequestSpec())
                .when().get(String.format(GET_DATA_FROM_INDEX, indexName))
                .then().assertThat().spec(ResponseSpecs.baseResponseSpec()).statusCode(HttpStatus.SC_OK)
                .extract().response();
    }
}