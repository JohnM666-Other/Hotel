package com.hotel.services;

import com.hotel.dto.HotelViewModel;
import com.hotel.entities.Feedback;
import com.hotel.entities.Hotel;
import com.hotel.repositories.HotelRepository;
import com.hotel.repositories.HotelSearchManager;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {

    private HotelRepository hotelRepository;
    private HotelSearchManager hotelSearchManager;

    public HotelService(HotelRepository hotelRepository, HotelSearchManager hotelSearchManager) {
        this.hotelRepository = hotelRepository;
        this.hotelSearchManager = hotelSearchManager;
    }

    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    public Hotel getById(Long id) {
        return hotelRepository.findById(id).orElseThrow(()->new ObjectNotFoundException(id, "Hotel"));
    }

    public Hotel create(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel update(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public void deleteById(Long id) {
        hotelRepository.deleteById(id);
    }

    public HotelViewModel getHotelInfo(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElse(null);

        if(hotel == null) {
            return null;
        }

        float totalScore = 0.0f;

        for(Feedback f : hotel.getFeedbacks()) {
            totalScore += f.getScore();
        }

        float avgScore =
                !hotel.getFeedbacks().isEmpty() ? (totalScore / hotel.getFeedbacks().size()) : 0.0f;

        return new HotelViewModel(
            hotel.getId(),
            hotel.getName(),
            hotel.getDescription(),
            hotel.getScore(),
            avgScore,
            hotel.getCountry(),
            hotel.getCity(),
            hotel.getUrl());
    }

    public List<HotelViewModel> getHotelsInfo() {
        List<HotelViewModel> hotels = new ArrayList<>();

        for(Hotel hotel : getAll()) {
            float totalScore = 0.0f;

            for(Feedback f : hotel.getFeedbacks()) {
                totalScore += f.getScore();
            }

            float avgScore =
                    !hotel.getFeedbacks().isEmpty() ? (totalScore / hotel.getFeedbacks().size()) :
                    0.0f;

            HotelViewModel model = new HotelViewModel(
                    hotel.getId(),
                    hotel.getName(),
                    hotel.getDescription(),
                    hotel.getScore(),
                    avgScore,
                    hotel.getCountry(),
                    hotel.getCity(),
                    hotel.getUrl());
            hotels.add(model);
        }

        return hotels;
    }

    public List<Hotel> searchHotels(String text) {
        return new ArrayList<Hotel>();
    }
}
