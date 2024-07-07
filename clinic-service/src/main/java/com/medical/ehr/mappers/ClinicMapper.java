package com.medical.ehr.mappers;

import com.medical.ehr.dto.requests.AddClinicRequest;
import com.medical.ehr.dto.requests.EditClinicRequest;
import com.medical.ehr.dto.responses.ClinicResponse;
import com.medical.ehr.models.Clinic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClinicMapper {

    public Clinic mapToClinic(AddClinicRequest clinicRequest) {
        return Clinic.builder()
                .name(clinicRequest.name())
                .address(clinicRequest.address())
                .phoneNumber(clinicRequest.phoneNumber())
                .build();
    }

    public Clinic mapToClinic(Clinic clinic, EditClinicRequest editClinicRequest) {
        clinic.setName(editClinicRequest.name());
        clinic.setAddress(editClinicRequest.address());
        clinic.setPhoneNumber(editClinicRequest.phoneNumber());
        return clinic;
    }

    public ClinicResponse mapToClinicResponse(Clinic clinic) {
        return ClinicResponse.builder()
                .clinicId(clinic.getId())
                .name(clinic.getName())
                .address(clinic.getAddress())
                .phoneNumber(clinic.getPhoneNumber())
                .build();
    }

}
