package com.example.solution.repository;

import com.example.solution.model.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
    Optional<Tariff> findByTariffId(Long tariffId);
}
