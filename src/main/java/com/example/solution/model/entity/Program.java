package com.example.solution.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(indexes = {
        @Index(columnList = "admitadId", unique = true),
})
public class Program extends BaseEntity {
    private String name;
    private String image;
    private String gotoLink;
    private Long admitadId;
    private String productsXmlLink;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ActionsDetail> actionsDetail;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<ProgramCategory> categories;
}
