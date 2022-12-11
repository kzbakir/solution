package com.example.solution.service.impl;

import com.example.solution.model.entity.*;
import com.example.solution.repository.ProgramCategoryRepository;
import com.example.solution.repository.ProgramRepository;
import com.example.solution.repository.TariffRepository;
import com.example.solution.service.ProgramService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgramServiceImpl implements ProgramService {
    private final TariffRepository tariffRepository;
    private final ProgramRepository programRepository;
    private final ProgramCategoryRepository categoryRepository;

    @Override
    public void save(JsonNode jsonNode) {
        var results = jsonNode.get("results");
        for (var result : results) {
            var admintadId = result.get("id").asLong();
            var name = result.get("name").asText();
            var gotolink = result.get("gotolink").asText();
            var image = result.get("image").asText();
            var productsXmlLink = result.get("products_xml_link").asText(null);
            var actionDetailsNode = result.get("actions_detail");
            var categoriesNode = result.get("categories");
            var existProgram = programRepository.findByAdmitadId(admintadId)
                    .orElseGet(
                            () -> {
                                var p = new Program();
                                p.setAdmitadId(admintadId);
                                return p;
                            });
            existProgram.setName(name);
            existProgram.setGotoLink(gotolink);
            existProgram.setImage(image);
            existProgram.setProductsXmlLink(productsXmlLink);
            if (actionDetailsNode != null) {
                existProgram.setActionsDetail(extractActionsDetail(actionDetailsNode));
            }
            if (categoriesNode != null) {
                existProgram.setCategories(extractCategories(categoriesNode));
            }
            programRepository.save(existProgram);
        }
    }

    @Override
    public List<Program> getAllPrograms() {
        return programRepository.findByProductsXmlLinkIsNotNull();
    }

    private List<ActionsDetail> extractActionsDetail(JsonNode jsonNode) {
        var actionsDetail = new ArrayList<ActionsDetail>();
        for (var node : jsonNode) {
            var tariffs = extractTariffs(node.get("tariffs"));
            var type = node.get("type").asText("");
            var name = node.get("name").asText("");
             actionsDetail.add(ActionsDetail.builder()
                    .name(name)
                    .type(type)
                    .tariffs(tariffs)
                    .build());
        }
        return actionsDetail;
    }

    private Set<ProgramCategory> extractCategories(JsonNode jsonNode) {
        var categories = new HashSet<ProgramCategory>();
        for (var node : jsonNode) {
            var name = node.get("name").asText("");
            var categoryId = node.get("id").asLong();
            var existCategory = categoryRepository.findByCategoryId(categoryId)
                    .orElseGet(
                            () -> {
                                var c = new ProgramCategory();
                                c.setCategoryId(categoryId);
                                return c;
                            });
            existCategory.setName(name);
            categories.add(existCategory);
        }
        return categories;
    }

    private List<Tariff> extractTariffs(JsonNode jsonNode) {
        var tariffs = new ArrayList<Tariff>();
        for (var node : jsonNode) {
            var name = node.get("name").asText("");
            var tariffId = node.get("id").asLong(0);
            var ratesNode = node.get("rates");
            var existTariff = tariffRepository.findByTariffId(tariffId)
                    .orElseGet(
                            () -> {
                                var t = new Tariff();
                                t.setTariffId(tariffId);
                                return t;
                            });
            if (ratesNode != null) {
                existTariff.setRates(extractRates(ratesNode));
            }
            existTariff.setName(name);
            tariffs.add(existTariff);
        }
        return tariffs;
    }

    private List<Rate> extractRates(JsonNode jsonNode) {
        var rates = new ArrayList<Rate>();
        for (var node : jsonNode) {
            var price = node.get("price_s").asDouble(0);
            var size = node.get("size").asDouble(0);
            var isPercentage = node.get("is_percentage").asBoolean(false);
            rates.add(Rate.builder()
                    .isPercentage(isPercentage)
                    .price(price)
                    .size(size)
                    .build());
        }
        return rates;
    }
}
