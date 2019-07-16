package com.elk.exceptions;

import lombok.Getter;

import java.io.IOException;

@Getter
public class ElkNativeWebServiceException extends RuntimeException {

    private String message;
    private String customMessage;

    public ElkNativeWebServiceException(IOException ex, String customMessage) {
       this.message = ex.getMessage();
       this.customMessage = customMessage;
    }
}