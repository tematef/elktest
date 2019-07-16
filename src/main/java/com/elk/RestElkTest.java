package com.elk;

import org.apache.http.HttpHost;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.Client;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;

public class RestElkTest {
    public static void main(String[] args) {
//        RestClient restClient = RestClient.builder(
//                new HttpHost("localhost", 9200, "http"))
////                new HttpHost("localhost", 9201, "http"))
//                .build();

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));



        CreateIndexRequest request = new CreateIndexRequest("twitter3");
        CreateIndexRequest request1 = request.settings(Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 1)
        );


        try {
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            int a=1;
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
