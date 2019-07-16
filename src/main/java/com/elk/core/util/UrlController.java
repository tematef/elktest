package com.elk.core.util;

import lombok.AllArgsConstructor;

import static com.elk.core.properties.PropertiesController.getProperty;
import static com.elk.core.properties.PropertiesNames.ELASTIC_HOST;
import static com.elk.core.properties.PropertiesNames.ELASTIC_PORT;

public final class UrlController {

    public static final String ELASTIC_URL = formatUrl(UrlTemplate.URL_WITH_PORT, ELASTIC_HOST, ELASTIC_PORT);

    private UrlController() {
    }

    private static String formatUrl(UrlTemplate template, String host, String port) {
        return String.format(template.value, getProperty(host), getProperty(port));
    }

    @AllArgsConstructor
    public enum UrlTemplate {

        URL_WITH_PORT("http://%s:%s");

        private String value;
    }
}
