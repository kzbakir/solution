package com.example.solution.model.entity;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tariff extends BaseEntity {
    private String name;
    private Long tariffId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Rate> rates;
}
