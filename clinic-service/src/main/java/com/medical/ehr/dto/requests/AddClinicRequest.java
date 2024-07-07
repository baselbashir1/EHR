package com.medical.ehr.dto.requests;

import jakarta.validation.constraints.NotNull;

public record AddClinicRequest(
        @NotNull(message = "Name is required.")
        String name,
        @NotNull(message = "Address is required.")
        String address,
        @NotNull(message = "Phone number is required.")
        String phoneNumber
) {
}
