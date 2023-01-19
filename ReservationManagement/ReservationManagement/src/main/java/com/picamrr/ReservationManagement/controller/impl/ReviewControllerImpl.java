package com.picamrr.ReservationManagement.controller.impl;

import com.picamrr.ReservationManagement.controller.ReviewController;
import com.picamrr.ReservationManagement.model.Review;
import com.picamrr.ReservationManagement.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewControllerImpl implements ReviewController {

    private final ReviewService reviewService;

    @Override
    public ResponseEntity<Review> save(UserDetails user, Long restaurantId, Long reservationId, Review review) {
        return new ResponseEntity<>(reviewService.save(user.getUsername(), restaurantId, reservationId, review), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Review>> getReviewsForRestaurant(String restaurantId) {
        System.out.println("Got the request");
        return new ResponseEntity<>(reviewService.getReviewsForRestaurant(Long.parseLong(restaurantId)), HttpStatus.OK);
    }
}
