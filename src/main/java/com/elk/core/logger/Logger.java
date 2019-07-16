package com.elk.core.logger;

import org.slf4j.LoggerFactory;

public class Logger {

    public static org.slf4j.Logger out = LoggerFactory.getLogger(Logger.class.getName());

    private Logger() {
    }
}