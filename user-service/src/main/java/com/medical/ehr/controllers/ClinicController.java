package com.medical.ehr.controllers;

import com.medical.ehr.dto.requests.AddClinicRequest;
import com.medical.ehr.dto.requests.EditClinicRequest;
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
    public ResponseEntity<Object> getClinicByName(@RequestParam("name") String name) {
        return new ResponseEntity<>(clinicService.getClinicByName(name), HttpStatus.OK);
    }

    @GetMapping("/showClinics")
    public ResponseEntity<Object> showClinics() {
        return new ResponseEntity<>(clinicService.showClinics(), HttpStatus.OK);
    }

    @PostMapping("/addClinic")
    public ResponseEntity<Object> addClinic(@RequestBody AddClinicRequest addClinicRequest) {
        return new ResponseEntity<>(clinicService.addClinic(addClinicRequest), HttpStatus.CREATED);
    }

    @PostMapping("/editClinic/{clinicId}")
    public ResponseEntity<Object> editClinic(@RequestBody EditClinicRequest editClinicRequest, @PathVariable("clinicId") Long clinicId) {
        return new ResponseEntity<>(clinicService.editClinic(editClinicRequest, clinicId), HttpStatus.OK);
    }

    @DeleteMapping("/deleteClinic/{clinicId}")
    public ResponseEntity<Object> deleteClinic(@PathVariable("clinicId") Long clinicId) {
        return new ResponseEntity<>(clinicService.deleteClinic(clinicId), HttpStatus.OK);
    }

}
