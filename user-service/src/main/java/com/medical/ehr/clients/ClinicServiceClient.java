package com.medical.ehr.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "clinic-service", url = "http://localhost:8082/api/clinic")
public interface ClinicServiceClient {

    @GetMapping("/getByName")
    ResponseEntity<Object> getClinicByName(@RequestParam String name);
}
