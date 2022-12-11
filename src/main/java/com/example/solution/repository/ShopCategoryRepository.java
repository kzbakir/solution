package com.example.solution.repository;

import com.example.solution.model.entity.Shop;
import com.example.solution.model.entity.ShopCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopCategoryRepository extends PagingAndSortingRepository<ShopCategory, Long> {
    Optional<ShopCategory> findByCategoryId(Long categoryId);

    List<ShopCategory> findByIdIn(List<Long> categoryIds);

    Page<ShopCategory> findByIdIn(List<Long> ids, Pageable pageable);
}
