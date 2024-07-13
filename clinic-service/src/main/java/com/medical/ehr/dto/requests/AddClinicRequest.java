package com.medical.ehr.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AddClinicRequest(
        @NotNull(message = "Name is required.")
        @NotEmpty(message = "Name is required.")
        String name,
        @NotNull(message = "Address is required.")
        @NotEmpty(message = "Address is required.")
        String address,
        @NotNull(message = "Phone number is required.")
        @NotEmpty(message = "Phone number is required.")
        String phoneNumber
) {
}
