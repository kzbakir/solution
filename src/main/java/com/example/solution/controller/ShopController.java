package com.example.solution.controller;

import com.example.solution.model.dto.UpdateCategoriesRequest;
import com.example.solution.model.entity.Shop;
import com.example.solution.model.entity.ShopCategory;
import com.example.solution.repository.ShopCategoryRepository;
import com.example.solution.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shop")
public class ShopController {
    private final ShopRepository shopRepository;
    private final ShopCategoryRepository categoryRepository;

    @GetMapping()
    public Page<Shop> findAll(Pageable pageable) {
        return shopRepository.findAll(pageable);
    }

    @GetMapping("/category")
    public Page<ShopCategory> getCategories(Pageable pageable) {
       return categoryRepository.findAll(pageable);
    }

    @GetMapping("/category/{shopId}")
    public Page<ShopCategory> getCategoryByShopId(@PathVariable Long shopId, Pageable pageable) {
        var shop = shopRepository.findById(shopId);
        if (shop.isEmpty()) {
            throw new IllegalArgumentException("Shop not exist: " + shopId);
        }
        var categoriesId = shop.get().getCategories()
                .stream()
                .map(ShopCategory::getId)
                .collect(Collectors.toList());
        return categoryRepository.findByIdIn(categoriesId, pageable);
    }

    @PostMapping("/category/{shopId}")
    public ResponseEntity<String> updateCategories(@PathVariable Long shopId, @RequestBody UpdateCategoriesRequest request) {
        var optionalShop = shopRepository.findById(shopId);
        if (optionalShop.isEmpty()) {
            throw new IllegalArgumentException("Shop not exist: " + shopId);
        }
        var categories = categoryRepository.findByIdIn(request.getCategoryIds());
        var shop = optionalShop.get();
        shop.setCategories(categories);
        shopRepository.save(shop);
        return new ResponseEntity<>("Categories was updated", HttpStatus.ACCEPTED);
    }
}
