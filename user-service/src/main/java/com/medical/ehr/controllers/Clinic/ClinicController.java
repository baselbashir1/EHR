package com.medical.ehr.controllers.Clinic;

import com.medical.ehr.clients.ClinicServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/clinic")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicServiceClient clinicServiceClient;

    @GetMapping("/getByName")
    public ResponseEntity<Object> getClinicByName(@RequestParam String name) {
        return new ResponseEntity<>(clinicServiceClient.getClinicByName(name), HttpStatus.OK);
    }

}
