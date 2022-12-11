package com.example.solution.repository;

import com.example.solution.model.entity.ProgramCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgramCategoryRepository extends JpaRepository<ProgramCategory, Long> {
    Optional<ProgramCategory> findByCategoryId(Long categoryId);
}
