package com.hotel.controllers;

import com.hotel.dto.FeedbackSortType;
import com.hotel.dto.FeedbackType;
import com.hotel.entities.Feedback;
import com.hotel.entities.Hotel;
import com.hotel.repositories.FeedbackRepository;
import com.hotel.services.FeedbackService;
import com.hotel.services.HotelService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(FeedbackController.ROOT)
public class FeedbackController {

    private FeedbackService feedbackService;
    private HotelService hotelService;
    public final static String ROOT = "/api/feedbacks";

    public FeedbackController(FeedbackService feedbackService, HotelService hotelService) {
        this.feedbackService = feedbackService;
        this.hotelService = hotelService;
    }

    @GetMapping
    public List<Feedback> getAll() {
        return feedbackService.getAll();
    }

    @GetMapping("/{id}")
    public Feedback getById(@PathVariable("id") Long id) {
        return feedbackService.getById(id);
    }

    @PostMapping
    public Feedback create(@RequestBody Feedback feedback) {
        return feedbackService.create(feedback);
    }

    @PutMapping
    public Feedback update(@RequestBody Feedback feedback) {
        return feedbackService.update(feedback);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        feedbackService.deleteById(id);
    }

    @GetMapping("/search")
    public List<Feedback> searchFeedbacks(
            @RequestParam Long hotelId,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) FeedbackType type,
            @RequestParam(required = false) FeedbackSortType sort) {
        Hotel hotel = hotelService.getById(hotelId);
        return feedbackService.searchHotelFeedbacks(hotel, minAge, maxAge, type, sort);
    }
}
