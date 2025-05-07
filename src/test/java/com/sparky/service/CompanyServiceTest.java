package com.sparky.service;

import com.sparky.Domain.Company;
import com.sparky.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCompany_shouldSaveAndReturnCompany() {
        Company company = new Company();
        company.setName("Test Company");

        when(companyRepository.save(any())).thenReturn(company);

        Company result = companyService.createCompany(company);

        assertEquals("Test Company", result.getName());
        verify(companyRepository).save(company);
    }

    @Test
    void getAllCompanies_shouldReturnList() {
        when(companyRepository.findAll()).thenReturn(List.of(new Company(), new Company()));

        List<Company> result = companyService.getAllCompanies();

        assertEquals(2, result.size());
    }

    @Test
    void getCompanyById_existingId_shouldReturnCompany() {
        Company c = new Company();
        c.setId(1L);
        when(companyRepository.findById(1L)).thenReturn(Optional.of(c));

        Optional<Company> result = companyService.getCompanyById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void updateCompany_shouldModifyFields() {
        Company existing = new Company();
        existing.setId(1L);
        existing.setName("Old");
        existing.setRuc("000");

        Company updated = new Company();
        updated.setName("New");
        updated.setRuc("111");

        when(companyRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(companyRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Company result = companyService.updateCompany(1L, updated);

        assertEquals("New", result.getName());
        assertEquals("111", result.getRuc());
    }

    @Test
    void toggleCompanyStatus_shouldFlipActive() {
        Company c = new Company();
        c.setActive(false);

        when(companyRepository.findById(1L)).thenReturn(Optional.of(c));

        companyService.toggleCompanyStatus(1L);

        assertTrue(c.isActive());
        verify(companyRepository).save(c);
    }
}
