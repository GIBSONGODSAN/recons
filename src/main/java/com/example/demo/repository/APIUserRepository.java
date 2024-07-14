package com.example.demo.repository;

import com.example.demo.model.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface APIUserRepository extends JpaRepository<ApiUser, Integer> {
    Optional<ApiUser> findById(Integer id);
    ApiUser findByToken(String token);
}

