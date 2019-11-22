package com.hotel.controllers;

import com.hotel.entities.Hotel;
import com.hotel.repositories.HotelRepository;
import com.hotel.services.HotelService;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(HotelController.ROOT)
public class HotelController {

    private HotelService hotelService;
    public static final String ROOT = "/api/hotels";
    private static final Logger logger = LoggerFactory.getLogger(HotelController.class);

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public List<Hotel> getAll() {
        logger.info("Get all hotels");
        return hotelService.getAll();
    }

    @GetMapping("/{id}")
    public Hotel getById(@PathVariable("id") Long id) {
        logger.info("Get hotel by id {}", id);
        return hotelService.getById(id);
    }

    @PostMapping
    public Hotel create(@RequestBody Hotel hotel) {
        logger.info("Create hotel with {}", hotel);
        return hotelService.create(hotel);
    }

    @PostMapping("/update")
    public void update(HttpServletResponse http,
            @RequestParam Integer id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String country,
            @RequestParam String city,
            @RequestParam String url,
            @RequestParam Integer score) throws IOException {
        Hotel hotel = hotelService.getById((long)id);
        hotel.setName(name);
        hotel.setDescription(description);
        hotel.setCountry(country);
        hotel.setCity(city);
        hotel.setUrl(url);
        hotel.setScore(score);
        hotelService.update(hotel);
        logger.info("Update hotel {}", hotel);
        http.sendRedirect("/hotels");
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        logger.info("Delete hotel by id {}", id);
        hotelService.deleteById(id);
    }

    @GetMapping("/search")
    public List<Hotel> search(@RequestParam(value = "text", required = true) String text) {
        logger.info("Search ");
        return hotelService.searchHotels(text);
    }
}
