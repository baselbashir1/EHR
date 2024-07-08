package com.medical.ehr.controllers;

import com.medical.ehr.dto.requests.AddClinicRequest;
import com.medical.ehr.services.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @GetMapping("/getByName")
    public ResponseEntity<Object> getClinicByName(@RequestParam String name) {
        return new ResponseEntity<>(clinicService.getClinicByName(name), HttpStatus.OK);
    }

    @PostMapping("/addClinic")
    public ResponseEntity<Object> addClinic(@RequestBody AddClinicRequest addClinicRequest) {
        return new ResponseEntity<>(clinicService.addClinic(addClinicRequest), HttpStatus.CREATED);
    }

}
