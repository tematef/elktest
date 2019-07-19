package com.elk.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public final class YamlMapper {

    private ObjectMapper mapper;

    public YamlMapper() {
        mapper = new ObjectMapper(new YAMLFactory());
    }

    public <T>T map(String sourceYaml, Class<T> destinationClass) {
        File file = FileUtil.writeToFile(
                FileUtil.createTempFile("tmpfile" + FakeUtil.faker().number().numberBetween(0, 100)), sourceYaml);
        try {
            return mapper.readValue(file, destinationClass);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Error occurred while mapping source yaml: %s", sourceYaml), e);
        } finally {
            file.delete();
        }
    }
}