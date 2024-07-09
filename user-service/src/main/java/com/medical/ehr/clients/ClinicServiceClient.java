package com.medical.ehr.clients;

import com.medical.ehr.dto.requests.AddClinicRequest;
import com.medical.ehr.dto.requests.EditClinicRequest;
import com.medical.ehr.dto.responses.ClinicResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "clinic-service", url = "${clinic.service.url}")
public interface ClinicServiceClient {

    @GetMapping("/getByName")
    ResponseEntity<ClinicResponse> getClinicByName(@RequestParam("name") String name);

    @GetMapping("/getAllClinics")
    ResponseEntity<List<ClinicResponse>> getAllClinics();

    @PostMapping("/addClinic")
    ResponseEntity<String> addClinic(@RequestBody AddClinicRequest addClinicRequest);

    @PostMapping("/editClinic/{clinicId}")
    ResponseEntity<String> editClinic(@RequestBody EditClinicRequest editClinicRequest, @PathVariable("clinicId") Long clinicId);

    @DeleteMapping("/deleteClinic/{clinicId}")
    ResponseEntity<String> deleteClinic(@PathVariable("clinicId") Long clinicId);

}
