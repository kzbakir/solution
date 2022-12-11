package com.example.solution.service.impl;

import com.example.solution.model.xml.CatalogXml;
import com.example.solution.model.xml.CategoryXml;
import com.example.solution.model.xml.OfferXml;
import com.example.solution.model.xml.ShopXml;
import com.example.solution.resolver.ShopResolver;
import com.example.solution.service.XMLService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;

import java.net.URL;
import java.util.ArrayList;

@Service
@Slf4j
public class XMLServiceImpl implements XMLService {

    @Override
    public CatalogXml parseCatalog(String url) {
        var catalogXml = new CatalogXml();
        try {
            var connection = new URL(url).openConnection();
            var factory = DocumentBuilderFactory.newInstance();
            var builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new ShopResolver());
            var doc = builder.parse(connection.getInputStream());
            doc.getDocumentElement().normalize();
            var shopNode = doc.getElementsByTagName("shop").item(0);
            if (shopNode.getNodeType() == Node.ELEMENT_NODE) {
                var element = (Element) shopNode;
                var offers = new ArrayList<OfferXml>();
                var categories = new ArrayList<CategoryXml>();
                var offerNodeList = element.getElementsByTagName("offer");
                var categoryNodeList = element.getElementsByTagName("category");
                for (var i = 0; i < offerNodeList.getLength(); i++) {
                    var offerNode = offerNodeList.item(i);
                    if (offerNode.getNodeType() == Node.ELEMENT_NODE) {
                        var offerElement = (Element) offerNode;
                        offers.add(OfferXml.builder()
                                .offerId(getAttributeValue(offerElement, "id"))
                                .name(getTextContent(offerElement, "name"))
                                .picture(getTextContent(offerElement, "picture"))
                                .price(Double.parseDouble(getTextContent(offerElement, "price")))
                                .vendor(getTextContent(offerElement, "vendor"))
                                .url(getTextContent(offerElement, "url"))
                                .build());
                    }
                }
                for (var i = 0; i < categoryNodeList.getLength(); i++) {
                    var categoryNode = offerNodeList.item(i);
                    if (categoryNode.getNodeType() == Node.ELEMENT_NODE) {
                        var categoryElement = (Element) categoryNode;
                        categories.add(CategoryXml.builder()
                                .name(getTextContent(categoryElement, "name"))
                                .parentId(getAttributeValue(categoryElement, "parentId"))
                                .id(getAttributeValue(categoryElement, "id"))
                                .build());
                    }
                }
                catalogXml = CatalogXml.builder()
                        .shop(ShopXml.builder()
                                .name(getTextContent(element, "name"))
                                .url(getTextContent(element, "url"))
                                .offers(offers)
                                .categories(categories)
                                .build())
                        .build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return catalogXml;
    }

    private static String getTextContent(Element element, String tagName) {
        if (element.getElementsByTagName(tagName).getLength() == 0) {
            return null;
        }
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }

    private static Long getAttributeValue(Element element, String attributeName) {
        if (!element.hasAttribute(attributeName)) {
            return null;
        }
        return Long.parseLong(element.getAttribute(attributeName));
    }
}
