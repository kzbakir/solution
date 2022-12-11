package com.example.solution.repository;

import com.example.solution.model.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ShopRepository extends PagingAndSortingRepository<Shop, Long> {
    Optional<Shop> findByName(String name);
}
