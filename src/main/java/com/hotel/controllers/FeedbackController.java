package com.hotel.controllers;

import com.hotel.dto.FeedbackSortType;
import com.hotel.dto.FeedbackType;
import com.hotel.entities.Feedback;
import com.hotel.entities.Hotel;
import com.hotel.repositories.FeedbackRepository;
import com.hotel.services.FeedbackService;
import com.hotel.services.HotelService;
import com.hotel.services.UserService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(FeedbackController.ROOT)
public class FeedbackController {

    private FeedbackService feedbackService;
    private HotelService hotelService;
    private UserService userService;
    public final static String ROOT = "/api/feedbacks";

    public FeedbackController(FeedbackService feedbackService, HotelService hotelService, UserService userService) {
        this.feedbackService = feedbackService;
        this.hotelService = hotelService;
        this.userService = userService;
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
    @Secured("ROLE_USER")
    public Feedback create(@RequestBody Feedback feedback) {
        return feedbackService.create(feedback);
    }

    @PutMapping
    @Secured("ROLE_USER")
    public Feedback update(@RequestBody Feedback feedback) {
        return feedbackService.update(feedback);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    public void deleteById(@PathVariable("id") Long id) {
        feedbackService.deleteById(id);
    }

    @PostMapping("/send")
    @Secured("ROLE_USER")
    public void send(HttpServletResponse http,
                     @RequestParam("hotel") Long hotel,
                     @RequestParam("username") String username,
                     @RequestParam("score") Integer score,
                     @RequestParam("message") String message)
            throws Exception {
        Feedback feedback = new Feedback();
        feedback.setScore(score);
        feedback.setText(message);
        feedback.setHotel(hotelService.getById(hotel));
        feedback.setVisitDate(new Date());
        feedback.setUserEntity(userService.getByEmail(username));
        feedbackService.create(feedback);
        http.sendRedirect("/hotels/" + hotel);
    }

    @GetMapping("/search")
    public List<Feedback> searchFeedbacks(
            @RequestParam Long hotelId,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) FeedbackType type,
            @RequestParam(required = false) FeedbackSortType sort,
            @RequestParam(required = false) Integer pageIndex,
            @RequestParam(required = false) Integer pageSize) {
        Hotel hotel = hotelService.getById(hotelId);
        return feedbackService.searchHotelFeedbacks(hotel, minAge, maxAge, type, sort, pageIndex, pageSize);
    }
}
