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
        @Index(columnList = "name"),
        @Index(columnList = "model"),
        @Index(columnList = "offerId", unique = true)
})
public class Offer extends BaseEntity {
    private Long offerId;
    private String name;
    private Double price;
    private String picture;
    private String url;
    private String model;
}
