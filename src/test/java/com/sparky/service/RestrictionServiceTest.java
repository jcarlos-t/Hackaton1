package com.sparky.service;

import com.sparky.Domain.Restriction;
import com.sparky.repository.RestrictionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestrictionServiceTest {

    @Mock
    private RestrictionRepository restrictionRepository;

    @InjectMocks
    private RestrictionService restrictionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRestriction_shouldSaveAndReturn() {
        Restriction r = new Restriction();
        r.setModelName("gpt-4");
        r.setMaxRequests(100);
        r.setMaxTokens(200);
        r.setWindowMinutes(10);

        when(restrictionRepository.save(any())).thenReturn(r);

        Restriction result = restrictionService.createRestriction(r);

        assertEquals("gpt-4", result.getModelName());
        assertEquals(100, result.getMaxRequests());
        assertEquals(200, result.getMaxTokens());
        assertEquals(10, result.getWindowMinutes());
    }

    @Test
    void getAllRestrictionsByCompany_shouldReturnList() {
        when(restrictionRepository.findByCompanyId(1L)).thenReturn(List.of(new Restriction(), new Restriction()));

        List<Restriction> result = restrictionService.getAllRestrictionsByCompany(1L);

        assertEquals(2, result.size());
    }

    @Test
    void updateRestriction_shouldModifyValues() {
        Restriction existing = new Restriction();
        existing.setModelName("old");
        existing.setMaxRequests(50);
        existing.setMaxTokens(500);
        existing.setWindowMinutes(5);

        Restriction update = new Restriction();
        update.setModelName("new");
        update.setMaxRequests(80);
        update.setMaxTokens(800);
        update.setWindowMinutes(15);

        when(restrictionRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(restrictionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Restriction result = restrictionService.updateRestriction(1L, update);

        assertEquals("new", result.getModelName());
        assertEquals(80, result.getMaxRequests());
        assertEquals(800, result.getMaxTokens());
        assertEquals(15, result.getWindowMinutes());
    }

    @Test
    void deleteRestriction_shouldCallDeleteById() {
        doNothing().when(restrictionRepository).deleteById(1L);

        restrictionService.deleteRestriction(1L);

        verify(restrictionRepository, times(1)).deleteById(1L);
    }
}
