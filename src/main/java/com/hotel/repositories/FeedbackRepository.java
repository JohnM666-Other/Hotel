package com.hotel.repositories;

import com.hotel.entities.Feedback;
import com.hotel.entities.Hotel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Period;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    public List<Feedback> findByHotel(Hotel hotel);

    @Query("SELECT f from feedbacks f inner join f.customer c where f.hotel = ?1 and f.score >= ?2 and f.score <= ?3 and f.visitDate - c.birthDate >= ?4 and f.visitDate - c.birthDate <= ?5")
    public List<Feedback> searchFeedbacks(Hotel hotel, Integer minScore, Integer maxScore, Double minAge, Double maxAge, Sort sort);
}
