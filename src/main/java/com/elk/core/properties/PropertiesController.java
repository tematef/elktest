package com.elk.core.properties;

import com.elk.core.logger.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

public final class PropertiesController {

    private static Properties properties = new Properties();
    private static PropertiesController instance;

    private PropertiesController() {
        loadProperties();
    }

    public static String getProperty(String propertyName) {
        Optional.ofNullable(instance).orElseGet(() -> instance = new PropertiesController());

        String systemPropertyValue = System.getProperty(propertyName);
        if (systemPropertyValue != null) {
            return systemPropertyValue;
        }

        return getPropertyWithCheck(propertyName);
    }

    public static int getIntProperty(String propertyName) {
        return Integer.parseInt(getProperty(propertyName));
    }

    private static String getPropertyWithCheck(String propertyName) {
        String propertyValue = properties.getProperty(propertyName);
        Optional.ofNullable(propertyValue).orElseThrow(
                () -> new RuntimeException(String.format("Property with %s name wasn't found", propertyName)));
        return propertyValue;
    }

    private void loadProperties() {
        URL props = ClassLoader.getSystemResource("common.properties");
        Logger.out.info("Reading properties: {}", props.getPath());
        try {
            properties.load(props.openStream());
            Logger.out.info("Loaded properties: {}", props.getPath());
        }
        catch (IOException e) {
            throw new RuntimeException(String.format("%s props file wasn't found", props.getPath()), e);
        }
    }
}
