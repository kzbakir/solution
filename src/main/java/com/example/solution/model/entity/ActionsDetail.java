package com.example.solution.model.entity;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ActionsDetail extends BaseEntity {
    private String type;
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Tariff> tariffs;
}
