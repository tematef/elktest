package com.elk.apiclients;

import com.elk.core.logger.Logger;
import com.elk.core.properties.PropertiesController;
import com.elk.exceptions.ElkNativeWebServiceException;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.io.IOException;
import java.util.Optional;

import static com.elk.core.properties.PropertiesNames.ELASTIC_HOST;
import static com.elk.core.properties.PropertiesNames.ELASTIC_HOST_SCHEMA;
import static com.elk.core.properties.PropertiesNames.ELASTIC_PORT;

public class ElkNativeRestOperator {

    private RestHighLevelClient client;
    private HttpHost httpHost;

    public ElkNativeRestOperator() {
        client = createClient();
    }

    private RestHighLevelClient createClient() {
        Optional.ofNullable(httpHost)
                .orElseGet(() -> httpHost = new HttpHost(PropertiesController.getProperty(ELASTIC_HOST),
                        PropertiesController.getIntProperty(ELASTIC_PORT),
                        PropertiesController.getProperty(ELASTIC_HOST_SCHEMA)));
        Optional.ofNullable(client)
                .orElseGet(() -> client = new RestHighLevelClient(RestClient.builder(httpHost)));
        Logger.out.info("RestHighLevelClient is created");
        return client;
    }

    public void close() {
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                Logger.out.error("Error occured while closing RestHighLevelClient: {}", e.getMessage());
            }
        }
    }

    public Actions getActions() {
        return new Actions();
    }

    public class Actions {

        public CreateIndexResponse createIndex(String indexName) {
            try {
                return client.indices().create(new CreateIndexRequest(indexName), RequestOptions.DEFAULT);
            } catch (IOException e) {
                String errorMessage = "Error occured while creating %s index";
                Logger.out.error(String.format(errorMessage, indexName));
                throw new ElkNativeWebServiceException(e, String.format(errorMessage, indexName));
            }
        }

        public AcknowledgedResponse deleteIndex(String indexName) {
            try {
                return client.indices().delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT);
            } catch (IOException e) {
                String errorMessage = "Error occured while deleting %s index";
                Logger.out.error(String.format(errorMessage, indexName));
                throw new ElkNativeWebServiceException(e, String.format(errorMessage, indexName));
            }
        }
    }
}