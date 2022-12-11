package com.example.solution.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "solution.admitad")
public class AdmitadProperties {
    private String clientId;
    private String clientSecret;
    private String apiUrl;
}
