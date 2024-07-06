package com.medical.ehr.repositories;

import com.medical.ehr.models.Secretary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecretaryRepository extends JpaRepository<Secretary, Long> {
}
