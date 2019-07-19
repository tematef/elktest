package com.elk.apiclients;

import com.elk.core.logger.Logger;
import com.elk.core.properties.PropertiesController;
import com.elk.exceptions.ElkNativeWebServiceException;
import io.qameta.allure.Step;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.io.IOException;
import java.util.Optional;

import static com.elk.core.properties.PropertiesNames.ELASTIC_CONNECTION_TIMEOUT;
import static com.elk.core.properties.PropertiesNames.ELASTIC_HOST;
import static com.elk.core.properties.PropertiesNames.ELASTIC_HOST_SCHEMA;
import static com.elk.core.properties.PropertiesNames.ELASTIC_PORT;
import static com.elk.core.util.WaitUtil.doWait;
import static org.assertj.core.api.Assertions.assertThat;

public class ElkNativeRestOperator {

    private static final long ELK_CONNECTION_TIMEOUT = PropertiesController.getLongProperty(ELASTIC_CONNECTION_TIMEOUT);
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
        Logger.out.info("waiting for elastic connection");
        getActions().waitClusterStartUp();
        Logger.out.info("RestHighLevelClient is created");
        return client;
    }

    public void close() {
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                Logger.out.error("Error occurred while closing RestHighLevelClient: {}", e.getMessage());
            }
        }
    }

    public Actions getActions() {
        return new Actions();
    }

    public class Actions {

        @Step("create {0} index")
        public CreateIndexResponse createIndex(String indexName) {
            try {
                return client.indices().create(new CreateIndexRequest(indexName), RequestOptions.DEFAULT);
            } catch (IOException e) {
                String errorMessage = "Error occurred while creating %s index";
                Logger.out.error(String.format(errorMessage, indexName));
                throw new ElkNativeWebServiceException(e, String.format(errorMessage, indexName));
            }
        }

        @Step("delete {0} index")
        public AcknowledgedResponse deleteIndex(String indexName) {
            try {
                return client.indices().delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT);
            } catch (IOException e) {
                String errorMessage = "Error occurred while deleting %s index";
                Logger.out.error(String.format(errorMessage, indexName));
                throw new ElkNativeWebServiceException(e, String.format(errorMessage, indexName));
            }
        }

        public ClusterHealthResponse getClusterHealth() {
            try {
                return client.cluster().health(new ClusterHealthRequest(), RequestOptions.DEFAULT);
            } catch (IOException e) {
                String errorMessage = "Error occurred while getting cluster health";
                Logger.out.error(errorMessage);
                throw new ElkNativeWebServiceException(e, errorMessage);
            }
        }

        private void waitClusterStartUp() {
            doWait(ELK_CONNECTION_TIMEOUT)
                    .ignoreException(ElkNativeWebServiceException.class)
                    .untilAsserted(() -> assertThat(getClusterHealth())
                            .as("Connection to elastic wasn't established").isNotNull());
        }
    }
}