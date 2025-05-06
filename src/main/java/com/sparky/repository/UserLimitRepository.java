package com.sparky.repository;
import com.sparky.Domain.UserLimit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLimitRepository extends JpaRepository<UserLimit, Long> {
    List<UserLimit> findByUserId(Long userId);
}
