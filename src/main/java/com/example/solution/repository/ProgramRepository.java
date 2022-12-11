package com.example.solution.repository;

import com.example.solution.model.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    Optional<Program> findByAdmitadId(Long id);

    List<Program> findByProductsXmlLinkIsNotNull();
}
