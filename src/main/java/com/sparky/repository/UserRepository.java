package com.sparky.repository;
import com.sparky.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByCompanyId(Long companyId);
    Optional<User> findByEmail(String email);

}
