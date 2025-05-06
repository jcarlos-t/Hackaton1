package com.sparky.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public Company updateCompany(Long id, Company updated) {
        Company existing = companyRepository.findById(id).orElseThrow();
        existing.setName(updated.getName());
        existing.setRuc(updated.getRuc());
        return companyRepository.save(existing);
    }

    public void toggleCompanyStatus(Long id) {
        Company company = companyRepository.findById(id).orElseThrow();
        company.setActive(!company.isActive());
        companyRepository.save(company);
    }
}