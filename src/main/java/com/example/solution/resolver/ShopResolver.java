package com.example.solution.resolver;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

public class ShopResolver implements EntityResolver {
    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        if (systemId.contains("shops.dtd")) {
            return new InputSource(new StringReader(""));
        } else {
            return null;
        }
    }
}
