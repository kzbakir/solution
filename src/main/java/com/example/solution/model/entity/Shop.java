package com.example.solution.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(indexes = {
        @Index(columnList = "name", unique = true)
})
public class Shop extends BaseEntity {
    private String name;
    private String url;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Offer> offers;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<ShopCategory> categories;

}
