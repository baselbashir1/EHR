package com.medical.ehr.services;

import com.medical.ehr.clients.ClinicServiceClient;
import com.medical.ehr.dto.requests.AddClinicRequest;
import com.medical.ehr.dto.responses.ClinicResponse;
import com.medical.ehr.utils.SecurityLayer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClinicService {

    private final ClinicServiceClient clinicServiceClient;
    private final SecurityLayer securityLayer;

    public ClinicResponse getClinicByName(String name) {
        try {
            return clinicServiceClient.getClinicByName(name).getBody();
        } catch (Exception e) {
            if (e.getMessage().contains("Clinic not found")) {
                throw new IllegalArgumentException("Clinic not found.");
            }
            throw e;
        }
    }

    public String addClinic(AddClinicRequest addClinicRequest) {
        securityLayer.authorizeAdmin();
        return clinicServiceClient.addClinic(addClinicRequest).getBody();
    }

}
