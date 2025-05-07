package com.sparky.controller;

import com.sparky.Domain.Company;
import com.sparky.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/setup")
public class SetupController {

    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping("/init-company")
    public ResponseEntity<Company> createInitialCompany(@RequestBody Company c) {
        return ResponseEntity.ok(companyRepository.save(c));
    }
}
