package com.example.solution.repository;

import com.example.solution.model.entity.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferRepository extends PagingAndSortingRepository<Offer, Long> {
    Optional<Offer> findByOfferId(Long id);

    @Query("SELECT o FROM Offer o WHERE o.name LIKE %:name% OR o.model LIKE %:model%")
    Page<Offer> search(String name, String model, Pageable pageable);
}
