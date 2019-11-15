package com.hotel.controllers;

import com.hotel.entities.Hotel;
import com.hotel.repositories.HotelRepository;
import com.hotel.services.HotelService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(HotelController.ROOT)
public class HotelController {

    private HotelService hotelService;
    public static final String ROOT = "/api/hotels";

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public List<Hotel> getAll() {
        return hotelService.getAll();
    }

    @GetMapping("/{id}")
    public Hotel getById(@PathVariable("id") Long id) {
        return hotelService.getById(id);
    }

    @PostMapping
    public Hotel create(@RequestBody Hotel hotel) {
        return hotelService.create(hotel);
    }

    @PutMapping
    public Hotel update(@RequestBody Hotel hotel) {
        return hotelService.update(hotel);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        hotelService.deleteById(id);
    }

    @GetMapping("/search")
    public List<Hotel> search(@RequestParam(value = "text", required = true) String text) {
        return hotelService.searchHotels(text);
    }
}
