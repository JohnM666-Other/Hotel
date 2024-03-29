package com.hotel.services;

import com.hotel.dto.FeedbackSortType;
import com.hotel.dto.FeedbackType;
import com.hotel.dto.FeedbackViewModel;
import com.hotel.entities.Feedback;
import com.hotel.entities.Hotel;
import com.hotel.repositories.FeedbackRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class FeedbackService {

    private FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<Feedback> getAll() {
        return feedbackRepository.findAll();
    }

    public Feedback getById(Long id) {
        return feedbackRepository.findById(id).orElseThrow(()->new ObjectNotFoundException(id, "Feedback"));
    }

    public Feedback create(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Feedback update(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);
    }

    public List<FeedbackViewModel> getHotelFeedbacks(Hotel hotel) {
        List<Feedback> feedbacks = feedbackRepository.findByHotel(hotel);
        List<FeedbackViewModel> viewModels = new ArrayList<>();

        for(Feedback feedback : feedbacks) {
            long millis = feedback.getVisitDate().getTime() - feedback.getUserEntity().getBirthDate().getTime();

            FeedbackViewModel viewModel = new FeedbackViewModel(
                feedback.getId(),
                    feedback.getHotel(),
                    feedback.getUserEntity(),
                    feedback.getVisitDate(),
                    feedback.getScore(),
                    feedback.getText(),
                    (int)TimeUnit.DAYS.convert(millis, TimeUnit.MILLISECONDS) / 365);
            viewModels.add(viewModel);
        }

        return viewModels;
    }

    public List<Feedback> searchHotelFeedbacks(Hotel hotel, Integer minAge, Integer maxAge, FeedbackType type, FeedbackSortType sort, Integer pageIndex, Integer pageSize) {
        int rMinAge = minAge == null ? 0 : minAge;
        int rMaxAge = maxAge == null ? 1000 : maxAge;
        int rMinScore = 0, rMaxScore = 0;

        if(type == null) {
            rMinScore = 0;
            rMaxScore = 100;
        } else if(type == FeedbackType.NEGATIVE) {
            rMinScore = 0;
            rMaxScore = 3;
        } else if(type == FeedbackType.POSITIVE) {
            rMinScore = 4;
            rMaxScore = 5;
        }

        Sort rSort = null;

        if(sort == FeedbackSortType.SCORE_LOWER) {
            rSort = new Sort(Sort.Direction.ASC, "score");
        } else if(sort == FeedbackSortType.SCORE_GREATER) {
            rSort = new Sort(Sort.Direction.DESC, "score");
        } else if(sort == FeedbackSortType.DATE_LOWER) {
            rSort = new Sort(Sort.Direction.ASC, "visitDate");
        } else if(sort == FeedbackSortType.DATE_GREATER) {
            rSort = new Sort(Sort.Direction.DESC, "visitDate");
        }

        Double minTime = (double)rMinAge * 365;
        Double maxTime = (double)rMaxAge * 365;

        Pageable pageable = null;

        if (pageIndex != null && pageSize != null) {
           pageable = rSort == null ? PageRequest.of(pageIndex, pageSize) : PageRequest.of(pageIndex, pageSize, rSort);
        }

        return feedbackRepository.searchFeedbacks(hotel, rMinScore, rMaxScore, minTime, maxTime, pageable);
    }
}
