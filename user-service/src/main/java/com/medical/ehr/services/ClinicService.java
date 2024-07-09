package com.medical.ehr.services;

import com.medical.ehr.clients.ClinicServiceClient;
import com.medical.ehr.dto.requests.AddClinicRequest;
import com.medical.ehr.dto.requests.EditClinicRequest;
import com.medical.ehr.dto.responses.ClinicResponse;
import com.medical.ehr.handlers.ClientException;
import com.medical.ehr.utils.SecurityLayer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClinicService {

    private final ClinicServiceClient clinicServiceClient;
    private final SecurityLayer securityLayer;
    private final ClientException clientException;

    public ClinicResponse getClinicByName(String name) {
        try {
            return clinicServiceClient.getClinicByName(name).getBody();
        } catch (Exception e) {
            clientException.handle(e, "Clinic");
            throw e;
        }
    }

    public List<ClinicResponse> showClinics() {
        try {
            securityLayer.authorizeAdmin();
            return clinicServiceClient.getAllClinics().getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String addClinic(AddClinicRequest addClinicRequest) {
        try {
            securityLayer.authorizeAdmin();
            return clinicServiceClient.addClinic(addClinicRequest).getBody();
        } catch (Exception e) {
            clientException.handle(e, "Clinic");
            throw e;
        }
    }

    public String editClinic(EditClinicRequest editClinicRequest, Long clinicId) {
        try {
            securityLayer.authorizeAdmin();
            return clinicServiceClient.editClinic(editClinicRequest, clinicId).getBody();
        } catch (Exception e) {
            clientException.handle(e, "Clinic");
            throw e;
        }
    }

    public String deleteClinic(Long clinicId) {
        try {
            securityLayer.authorizeAdmin();
            return clinicServiceClient.deleteClinic(clinicId).getBody();
        } catch (Exception e) {
            clientException.handle(e, "Clinic");
            throw e;
        }
    }

}
