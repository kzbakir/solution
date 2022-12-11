package com.example.solution.model.xml;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferXml {
    private Long offerId;
    private String name;
    private Double price;
    private String picture;
    private String vendor;
    private String url;
}
