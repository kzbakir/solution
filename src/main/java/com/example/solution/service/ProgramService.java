package com.example.solution.service;

import com.example.solution.model.entity.Program;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface ProgramService {
    void save(JsonNode jsonNode);

    List<Program> getAllPrograms();
}
