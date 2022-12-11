package com.example.solution.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(indexes = {
        @Index(columnList = "categoryId", unique = true),
})
public class ProgramCategory extends BaseEntity {
    private String name;
    private Long categoryId;
}
