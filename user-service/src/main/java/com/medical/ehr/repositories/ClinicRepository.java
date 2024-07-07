package com.medical.ehr.repositories;

import com.medical.ehr.models.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    Clinic findByName(String name);

}
