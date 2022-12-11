package com.example.solution.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateCategoriesRequest {
    private List<Long> categoryIds;
}
