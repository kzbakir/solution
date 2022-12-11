package com.example.solution.repository;

import com.example.solution.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByClientId(String clientId);

    void deleteByClientId(String clientId);
}
