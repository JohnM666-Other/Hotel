package com.hotel.repositories;

import com.hotel.entities.Hotel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("select h from hotels h where (lower(h.name) like lower(concat('%', ?1, '%')) or lower(h.country) like lower(concat('%', ?1, '%')) or lower(h.city) like lower(concat('%', ?1, '%'))) and h.score >= ?2")
    List<Hotel> searchHotels(String text, Integer minStar, Pageable pageable);
}