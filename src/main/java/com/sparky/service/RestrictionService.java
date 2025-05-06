package com.sparky.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sparky.entity.Restriction;
import com.sparky.repository.RestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestrictionService {

    @Autowired
    private RestrictionRepository restrictionRepository;

    public Restriction createRestriction(Restriction restriction) {
        return restrictionRepository.save(restriction);
    }

    public List<Restriction> getAllRestrictionsByCompany(Long companyId) {
        return restrictionRepository.findByCompanyId(companyId);
    }

    public Restriction updateRestriction(Long id, Restriction r) {
        Restriction existing = restrictionRepository.findById(id).orElseThrow();
        existing.setModelName(r.getModelName());
        existing.setMaxRequests(r.getMaxRequests());
        existing.setMaxTokens(r.getMaxTokens());
        existing.setWindowMinutes(r.getWindowMinutes());
        return restrictionRepository.save(existing);
    }

    public void deleteRestriction(Long id) {
        restrictionRepository.deleteById(id);
    }
}
