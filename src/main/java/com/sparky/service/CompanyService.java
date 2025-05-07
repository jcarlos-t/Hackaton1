package com.sparky.service;

import com.sparky.Domain.Company;
import com.sparky.Domain.User;
import com.sparky.repository.CompanyRepository;
import com.sparky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Company createCompany(Company company) {
        User admin = company.getAdmin();

        // Encriptar password
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        // Asignar rol y relación con la empresa
        admin.setRole(User.Role.COMPANY_ADMIN);
        admin.setCompany(company);

        // Guardar empresa primero (para tener ID)
        Company savedCompany = companyRepository.save(company);

        // Asociar empresa guardada al admin y guardar
        admin.setCompany(savedCompany);
        userRepository.save(admin);

        return savedCompany;
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
