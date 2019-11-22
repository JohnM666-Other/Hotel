package com.hotel.controllers;

import com.hotel.dto.FeedbackSortType;
import com.hotel.dto.FeedbackType;
import com.hotel.entities.CustomUser;
import com.hotel.entities.Hotel;
import com.hotel.entities.UserEntity;
import com.hotel.repositories.HotelRepository;
import com.hotel.services.FeedbackService;
import com.hotel.services.HotelService;
import com.hotel.services.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
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
    private UserService userService;

    public WebController(HotelService hotelService, FeedbackService feedbackService, UserService userService) {
        this.hotelService = hotelService;
        this.feedbackService = feedbackService;
        this.userService = userService;
    }

    @GetMapping("/hotels")
    public String index(Model model) {
        model.addAttribute("hotels", hotelService.getHotelsInfo());

        if(SecurityContextHolder.getContext().getAuthentication() != null &&
           SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
           !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        }

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

        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
            model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());

        return "hotel";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/profile")
    @Secured("ROLE_USER")
    public String profile(Model model) {
        UserEntity user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("firstname", user.getFirstname());
        model.addAttribute("lastname", user.getSecondname());
        model.addAttribute("birthdate", user.getBirthDate());
        model.addAttribute("sex", user.getSex());
        return "profile";
    }

    @GetMapping("/hotel/edit/{id}")
    @Secured("ROLE_ADMIN")
    public String editHotel(Model model, @PathVariable("id") Long id) {
        Hotel hotel = hotelService.getById(id);
        model.addAttribute("hotel", hotel);
        return "hotel-edit";
    }
}
