package com.elk.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class CustomMapMapper {

    private ObjectMapper mapper;

    public CustomMapMapper() {
        mapper = new ObjectMapper();
    }

    public <T>T map(Object sourceMap, Class<T> destinationClass) {
        byte[] json;
        try {
            json = mapper.writeValueAsBytes(sourceMap);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(String.format("Failed to get bytes from custom map: %s", sourceMap.toString()), e);
        }
        try {
            return mapper.readValue(json, destinationClass);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to map data to pojo", e);

        }
    }
}
