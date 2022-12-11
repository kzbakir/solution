package com.example.solution.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(indexes = {
        @Index(columnList = "categoryId", unique = true),
})
public class ShopCategory extends BaseEntity {
    private String name;
    private Long categoryId;
    private Long parentId;
}
