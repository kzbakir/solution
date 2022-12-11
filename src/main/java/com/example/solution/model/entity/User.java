package com.example.solution.model.entity;

import com.example.solution.model.enums.Role;
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
        @Index(columnList = "username", unique = true)
})
public class User extends BaseEntity {
    private String username;
    private String password;
    @Enumerated(EnumType.ORDINAL)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;
}
