package com.example.solution.controller;

import com.example.solution.model.dto.SearchOfferRequest;
import com.example.solution.model.entity.Offer;
import com.example.solution.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/offer")
public class OfferController {
    private final OfferRepository offerRepository;

    @GetMapping("/search")
    public Page<Offer> search(SearchOfferRequest request, Pageable pageable) {
        var searchText = request.getSearchText();
        return offerRepository.search(searchText, searchText, pageable);
    }

    @GetMapping()
    public Page<Offer> getAll(Pageable pageable) {
        return offerRepository.findAll(pageable);
    }
}
