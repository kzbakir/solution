package com.example.solution.model.xml;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryXml {
    private Long id;
    private String name;
    private Long parentId;
}
