package com.example.demo.repository;

import com.example.demo.model.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface APIUserRepository extends JpaRepository<ApiUser, Integer> {
    ApiUser findByToken(String token);
    ApiUser findByEmail(String email);
}

