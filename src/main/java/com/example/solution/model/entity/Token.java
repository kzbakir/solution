package com.example.solution.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(indexes = {
        @Index(columnList = "clientId", unique = true)
})
public class Token extends BaseEntity {
    private String clientId;
    private String accessToken;
    private String refreshToken;
    private Date expiresDate;
}
