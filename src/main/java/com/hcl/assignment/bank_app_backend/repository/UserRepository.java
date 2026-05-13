package com.hcl.assignment.bank_app_backend.repository;

import com.hcl.assignment.bank_app_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByPanNumber(String panNumber);

}
