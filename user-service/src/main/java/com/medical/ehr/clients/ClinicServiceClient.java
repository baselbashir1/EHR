package com.medical.ehr.clients;

import com.medical.ehr.dto.requests.AddClinicRequest;
import com.medical.ehr.dto.responses.ClinicResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "clinic-service", url = "${clinic.service.url}")
public interface ClinicServiceClient {

    @GetMapping("/getByName")
    ResponseEntity<ClinicResponse> getClinicByName(@RequestParam("name") String name);

    @PostMapping("/addClinic")
    ResponseEntity<String> addClinic(@RequestBody AddClinicRequest addClinicRequest);

}
