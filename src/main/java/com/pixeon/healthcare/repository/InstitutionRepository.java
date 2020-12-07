package com.pixeon.healthcare.repository;

import com.pixeon.healthcare.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Integer> {
    Institution findByCnpj(String cnpj);
}