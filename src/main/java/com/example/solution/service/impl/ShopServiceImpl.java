package com.example.solution.service.impl;

import com.example.solution.model.entity.Offer;
import com.example.solution.model.entity.Shop;
import com.example.solution.model.entity.ShopCategory;
import com.example.solution.model.xml.CategoryXml;
import com.example.solution.model.xml.OfferXml;
import com.example.solution.model.xml.ShopXml;
import com.example.solution.repository.OfferRepository;
import com.example.solution.repository.ShopCategoryRepository;
import com.example.solution.repository.ShopRepository;
import com.example.solution.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final ShopCategoryRepository categoryRepository;
    private final OfferRepository offerRepository;

    @Override
    @Transactional
    public void save(ShopXml xml) {
        if (xml == null) {
            return;
        }
        var existShop = shopRepository.findByName(xml.getName())
                .orElseGet(
                        () -> {
                            var s = new Shop();
                            s.setName(xml.getName());
                            return s;
                        });
        existShop.setUrl(xml.getUrl());
        existShop.setCategories(extractCategories(xml.getCategories()));
        existShop.setOffers(extractOffers(xml.getOffers()));
        shopRepository.save(existShop);
    }

    private List<ShopCategory> extractCategories(List<CategoryXml> categories) {
        var result = new ArrayList<ShopCategory>();
        for (var category : categories) {
            var existCategory = categoryRepository.findByCategoryId(category.getId())
                    .orElseGet(
                            () -> {
                                var c = new ShopCategory();
                                c.setCategoryId(category.getId());
                                return c;
                            });
            existCategory.setName(category.getName());
            existCategory.setParentId(category.getParentId());
            result.add(existCategory);
        }
        return result;
    }

    private List<Offer> extractOffers(List<OfferXml> offers) {
        var result = new ArrayList<Offer>();

        for (var offer : offers) {
            var existOffer = offerRepository.findByOfferId(offer.getOfferId())
                    .orElseGet(
                            () -> {
                                var o = new Offer();
                                o.setOfferId(offer.getOfferId());
                                return o;
                            });
            existOffer.setName(offer.getName());
            existOffer.setUrl(offer.getUrl());
            existOffer.setPicture(offer.getPicture());
            existOffer.setModel(offer.getVendor());
            existOffer.setPrice(offer.getPrice());
            result.add(existOffer);
        }
        return result;
    }
}
