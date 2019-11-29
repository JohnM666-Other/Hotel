package com.hotel.controllers;

import com.hotel.dto.FeedbackSortType;
import com.hotel.dto.FeedbackType;
import com.hotel.entities.CustomUser;
import com.hotel.entities.Feedback;
import com.hotel.entities.Hotel;
import com.hotel.entities.UserEntity;
import com.hotel.repositories.HotelRepository;
import com.hotel.services.FeedbackService;
import com.hotel.services.HotelService;
import com.hotel.services.UserService;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class WebController {

    private static final Logger logger = LoggerFactory.getLogger(WebController.class);

    private HotelService hotelService;
    private FeedbackService feedbackService;
    private UserService userService;

    public WebController(HotelService hotelService, FeedbackService feedbackService, UserService userService) {
        this.hotelService = hotelService;
        this.feedbackService = feedbackService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String root(Model model) {
        logger.info("/");
        return index(model);
    }

    @GetMapping("/hotels")
    public String index(Model model) {
        logger.info("/hotels");
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
                        HttpServletRequest request,
                        @PathVariable Long id,
                        @RequestParam(required = false) Integer minAge,
                        @RequestParam(required = false) Integer maxAge,
                        @RequestParam(required = false) FeedbackType type,
                        @RequestParam(required = false) FeedbackSortType sort) {
        logger.info("/hotels/{}", id);
        model.addAttribute("hotel", hotelService.getHotelInfo(id));
        model.addAttribute("feedbacks", feedbackService.getHotelFeedbacks(hotelService.getById(id)));
        model.addAttribute("isAdmin", request.isUserInRole("ROLE_ADMIN"));

        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
            model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());

        return "hotel";
    }

    @GetMapping("/signup")
    public String signup() {
        logger.info("/signup");
        return "signup";
    }

    @GetMapping("/profile")
    @Secured("ROLE_USER")
    public String profile(Model model) {
        logger.info("/profile");
        UserEntity user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("firstname", user.getFirstname());
        model.addAttribute("lastname", user.getSecondname());
        model.addAttribute("birthdate", user.getBirthDate());
        model.addAttribute("sex", user.getSex());
        return "profile";
    }

    @GetMapping("/hotels/edit/{id}")
    @Secured("ROLE_ADMIN")
    public String editHotel(Model model, @PathVariable("id") Long id) {
        logger.info("/hotels/edit/{}", id);

        try {
            Hotel hotel = hotelService.getById(id);
            model.addAttribute("hotel", hotel);
        } catch(ObjectNotFoundException e) {
            Hotel hotel = new Hotel();
            model.addAttribute("hotel", hotel);
        }

        return "hotel-edit";
    }

    @GetMapping("/login")
    public String login(Model model) {
        logger.info("/login");
        return "login";
    }

    @GetMapping("/search/{index}")
    public String searchHotels(
            Model model,
            @PathVariable("index") Integer index,
            @RequestParam("text") String text,
            @RequestParam(value = "minStars", required = false) Integer minStars) {
        logger.info("/hotels/{}?text={}&minStars={}", index, text, minStars);
        model.addAttribute("hotels", hotelService.getHotelsInfo(hotelService.searchHotels(text, minStars, index, 5)));
        model.addAttribute("page", index);
        model.addAttribute("text", text);

        if(minStars != null)
            model.addAttribute("minStars", minStars);

        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
            model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());

        return "index";
    }

    @GetMapping("/hotel/{hotelId}/{feedbackId}")
    public String editFeedback(
            Model model,
            HttpServletRequest request,
            @PathVariable("hotelId") Long hotelId,
            @PathVariable("feedbackId") Long feedbackId) {
        logger.info("/hotel/{}/{}", hotelId, feedbackId);

        Feedback feedback = feedbackService.getById(feedbackId);

        model.addAttribute("edit_score", feedback.getScore());
        model.addAttribute("edit_message", feedback.getText());
        model.addAttribute("edit_id", feedback.getId());
        model.addAttribute("hotel", hotelService.getHotelInfo(hotelId));
        model.addAttribute("feedbacks", feedbackService.getHotelFeedbacks(hotelService.getById(hotelId)));
        model.addAttribute("isAdmin", request.isUserInRole("ROLE_ADMIN"));

        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
            model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());

        return "hotel";
    }

    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    public String admin() {
        return "admin";
    }

    @GetMapping("/admin-hotels")
    @Secured("ROLE_ADMIN")
    public String adminHotels(Model model) {
        model.addAttribute("hotels", hotelService.getAll());

        if(SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        }

        return "admin-hotels";
    }
}
