package com.alvarogm.valuebay.domain.repository;

import com.alvarogm.valuebay.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAll();
    User findByUserId(Integer userId);
    User findByEmail(String email);
}
