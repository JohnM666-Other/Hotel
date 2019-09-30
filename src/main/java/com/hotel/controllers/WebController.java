package com.hotel.controllers;

import com.hotel.dto.FeedbackSortType;
import com.hotel.dto.FeedbackType;
import com.hotel.repositories.HotelRepository;
import com.hotel.services.FeedbackService;
import com.hotel.services.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

    private HotelService hotelService;
    private FeedbackService feedbackService;

    public WebController(HotelService hotelService, FeedbackService feedbackService) {
        this.hotelService = hotelService;
        this.feedbackService = feedbackService;
    }

    @GetMapping("/hotels")
    public String index(Model model) {
        model.addAttribute("hotels", hotelService.getHotelsInfo());
        return "index";
    }

    @GetMapping("/hotels/{id}")
    public String hotel(Model model,
                        @PathVariable Long id,
                        @RequestParam(required = false) Integer minAge,
                        @RequestParam(required = false) Integer maxAge,
                        @RequestParam(required = false) FeedbackType type,
                        @RequestParam(required = false) FeedbackSortType sort) {
        model.addAttribute("hotel", hotelService.getHotelInfo(id));
        model.addAttribute("feedbacks", feedbackService.getHotelFeedbacks(hotelService.getById(id)));
        return "hotel";
    }
}
