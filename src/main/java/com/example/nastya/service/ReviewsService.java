package com.example.nastya.service;

import com.example.nastya.entity.Reviews;
import com.example.nastya.repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewsService {

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private EventsService eventsService;

    @Autowired
    private UsersService usersService;

    public Reviews addReview(long idE, long idU, Reviews review) {
        review.setEvent(eventsService.findById(idE).get());
        review.setUser(usersService.getById(idU).get());
        return reviewsRepository.saveAndFlush(review);
    }

    public void delete(Long id) {
        reviewsRepository.deleteById(id);
    }

    public Reviews editReview(Reviews review) {
        return reviewsRepository.saveAndFlush(review);
    }

    public List<Reviews> getAll() {
        return reviewsRepository.findAll();
    }

    public List<Reviews> getReviewsByIdEvent(long idE) {
        return reviewsRepository.findReviewsByIdEvent(idE);
    }
}
