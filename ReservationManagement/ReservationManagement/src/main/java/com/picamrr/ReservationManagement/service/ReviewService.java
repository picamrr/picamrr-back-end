package com.picamrr.ReservationManagement.service;

import com.picamrr.ReservationManagement.model.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getReviewsForRestaurant(long id);

    Review save(String username, Long restaurantId, Long reservationId, Review review);
}
