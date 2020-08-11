package com.alvarogm.valuebay.domain.repository;

import com.alvarogm.valuebay.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(Integer userId);
}
