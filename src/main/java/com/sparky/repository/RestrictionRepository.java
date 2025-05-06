package com.sparky.repository;

import com.sparky.Domain.Restriction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestrictionRepository extends JpaRepository<Restriction, Long> {
    List<Restriction> findByCompanyId(Long companyId);
}
