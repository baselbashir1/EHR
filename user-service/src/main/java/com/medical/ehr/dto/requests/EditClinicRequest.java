package com.medical.ehr.dto.requests;

public record EditClinicRequest(
        String name,
        String address,
        String phoneNumber
) {
}
