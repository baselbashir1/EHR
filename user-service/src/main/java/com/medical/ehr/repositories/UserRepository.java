package com.medical.ehr.repositories;

import com.medical.ehr.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
