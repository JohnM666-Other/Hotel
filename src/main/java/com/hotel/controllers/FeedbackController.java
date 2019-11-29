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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(FeedbackController.ROOT)
public class FeedbackController {

    private FeedbackService feedbackService;
    private HotelService hotelService;
    private UserService userService;

    public final static String ROOT = "/api/feedbacks";
    private final static Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    public FeedbackController(FeedbackService feedbackService, HotelService hotelService, UserService userService) {
        this.feedbackService = feedbackService;
        this.hotelService = hotelService;
        this.userService = userService;
    }

    @GetMapping
    public List<Feedback> getAll() {
        logger.info("get all feedbacks");
        return feedbackService.getAll();
    }

    @GetMapping("/{id}")
    public Feedback getById(@PathVariable("id") Long id) {
        logger.info("get feedback with id {}", id);
        return feedbackService.getById(id);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public Feedback create(@RequestBody Feedback feedback) {
        logger.info("create feedback {}", feedback);
        return feedbackService.create(feedback);
    }

    @PutMapping
    @Secured("ROLE_ADMIN")
    public Feedback update(@RequestBody Feedback feedback) {
        logger.info("update feedback {}", feedback);
        return feedbackService.update(feedback);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public void deleteById(@PathVariable("id") Long id) {
        logger.info("delete feedback {}", id);
        feedbackService.deleteById(id);
    }

    @PostMapping("/send")
    @Secured("ROLE_USER")
    public void send(HttpServletResponse http,
                     @RequestParam("hotel") Long hotel,
                     @RequestParam("username") String username,
                     @RequestParam("score") Integer score,
                     @RequestParam("message") String message,
                     @RequestParam(value = "feedback", required = false) Long feedbackId)
            throws Exception {
        if(feedbackId == null) {
            Feedback feedback = new Feedback();
            feedback.setScore(score);
            feedback.setText(message);
            feedback.setHotel(hotelService.getById(hotel));
            feedback.setVisitDate(new Date());
            feedback.setUserEntity(userService.getByEmail(username));
            feedback = feedbackService.create(feedback);
            logger.info("send feedback: {}", feedback);
        } else {
            Feedback feedback = feedbackService.getById(feedbackId);

            if(feedback.getUserEntity().getEmail() != SecurityContextHolder.getContext().getAuthentication().getName()) {
                logger.info("edit feedback: auth failed: {}", SecurityContextHolder.getContext().getAuthentication().getName());
                http.sendError(403);
                return;
            }

            feedback.setScore(score);
            feedback.setText(message);
            feedback = feedbackService.update(feedback);
            logger.info("edit feedback: {}", feedback);
        }

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
        logger.info("search feedbacks");
        return feedbackService.searchHotelFeedbacks(hotel, minAge, maxAge, type, sort, pageIndex, pageSize);
    }

    @GetMapping("/user-delete/{hotel}/{id}")
    @Secured("ROLE_USER")
    public void userDelete(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("id") Long id,
            @PathVariable("hotel") Long hotelId) throws IOException {
        if(request.isUserInRole("ROLE_ADMIN") ||
                feedbackService.getById(id).getUserEntity().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            feedbackService.deleteById(id);
        }
        response.sendRedirect("/hotels/" + hotelId);
    }
}
