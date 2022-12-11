package com.example.solution.service;

import com.example.solution.model.xml.CatalogXml;

public interface XMLService {

    CatalogXml parseCatalog(String url);
}
