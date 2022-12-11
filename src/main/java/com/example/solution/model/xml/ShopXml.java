package com.example.solution.model.xml;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShopXml {
    private String name;
    private String url;
    List<OfferXml> offers;
    List<CategoryXml> categories;
}
