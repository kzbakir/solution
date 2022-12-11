package com.example.solution.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;

@MappedSuperclass
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BaseEntity {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "created_on")
    @CreationTimestamp
    private ZonedDateTime createdOn;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private ZonedDateTime updatedOn;

    @Column(name = "entity_version")
    @Version
    private Long entityVersion;
}
