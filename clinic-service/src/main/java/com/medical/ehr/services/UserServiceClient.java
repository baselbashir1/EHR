package com.medical.ehr.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserServiceClient {

    @Value("${user.service.url}")
    private String userServiceUrl;

    private final RestTemplate restTemplate;

    public void checkAdminPermission() {
        try {
            restTemplate.getForEntity(userServiceUrl + "/checkAdminPermission", Void.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
                throw new IllegalArgumentException("You don't have permission to perform this action.");
            }
            throw e;
        }
    }

}
