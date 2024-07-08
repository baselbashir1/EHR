package com.medical.ehr.handlers;

import org.springframework.stereotype.Component;

@Component
public class ClientException {

    public void handle(Exception e, String entityName) {
        if (e.getMessage().contains(entityName + " not found")) {
            throw new IllegalArgumentException(entityName + " not found.");
        }

        if (e.getMessage().contains(entityName + " already exists")) {
            throw new IllegalArgumentException(entityName + " already exists.");
        }
    }

}
