package com.sparky.repository;

import com.sparky.Domain.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
    List<RequestLog> findByUserId(Long userId);
}