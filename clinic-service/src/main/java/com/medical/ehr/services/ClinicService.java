package com.medical.ehr.services;

import com.medical.ehr.dto.requests.AddClinicRequest;
import com.medical.ehr.dto.requests.EditClinicRequest;
import com.medical.ehr.dto.responses.ClinicResponse;
import com.medical.ehr.mappers.ClinicMapper;
import com.medical.ehr.models.Clinic;
import com.medical.ehr.repositories.ClinicRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClinicService {

    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;

    public ClinicResponse getClinicByName(String name) {
        Clinic clinic = clinicRepository.findByName(name);
        if (clinic != null) {
            return clinicMapper.mapToClinicResponse(clinic);
        } else {
            throw new EntityNotFoundException("Clinic not found.");
        }
    }

    @Transactional(readOnly = true)
    public List<ClinicResponse> getAllClinics() {
        return clinicRepository.findAll()
                .stream()
                .map(clinicMapper::mapToClinicResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addClinic(AddClinicRequest addClinicRequest) {
        validateClinic(addClinicRequest.name());
        Clinic clinic = clinicMapper.mapToClinic(addClinicRequest);
        Clinic addedClinic = clinicRepository.save(clinic);
        log.info("Clinic {} added successfully.", addedClinic.getId());
    }

    @Transactional
    public void editClinic(EditClinicRequest editClinicRequest, Long clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new EntityNotFoundException("Clinic not found."));
        validateClinic(editClinicRequest.name());
        Clinic updatedClinic = clinicMapper.mapToClinic(clinic, editClinicRequest);
        clinicRepository.save(updatedClinic);
        log.info("Clinic {} updated successfully.", updatedClinic.getId());
    }

    @Transactional
    public void deleteClinic(Long clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new EntityNotFoundException("Clinic not found."));
        clinicRepository.delete(clinic);
        log.info("Clinic {} deleted successfully.", clinic.getId());
    }

    public void validateClinic(String name) {
        if (clinicRepository.findByName(name) != null) {
            throw new IllegalArgumentException("Clinic already exists.");
        }
    }

}
