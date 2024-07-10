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
@RequestMapping("/clinic")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @GetMapping("/getByName")
    public ResponseEntity<Object> getByName(@RequestParam("name") String name) {
        return new ResponseEntity<>(clinicService.getClinicByName(name), HttpStatus.OK);
    }

    @GetMapping("/getAllClinics")
    public ResponseEntity<Object> getAllClinics() {
        return new ResponseEntity<>(clinicService.getAllClinics(), HttpStatus.OK);
    }

    @PostMapping("/addClinic")
    public ResponseEntity<Object> addClinic(@RequestBody AddClinicRequest clinicRequest) {
        clinicService.addClinic(clinicRequest);
        return new ResponseEntity<>("Clinic added successfully.", HttpStatus.CREATED);
    }

    @PostMapping("/editClinic/{clinicId}")
    public ResponseEntity<Object> editClinic(@RequestBody EditClinicRequest editClinicRequest, @PathVariable("clinicId") Long clinicId) {
        clinicService.editClinic(editClinicRequest, clinicId);
        return new ResponseEntity<>("Clinic updated successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/deleteClinic/{clinicId}")
    public ResponseEntity<Object> deleteClinic(@PathVariable("clinicId") Long clinicId) {
        clinicService.deleteClinic(clinicId);
        return new ResponseEntity<>("Clinic deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/new")
    public ResponseEntity<Object> ss(@RequestHeader("loggedInUser") String username) {
        return new ResponseEntity<>("logged in user details: " + username, HttpStatus.OK);
    }

}
