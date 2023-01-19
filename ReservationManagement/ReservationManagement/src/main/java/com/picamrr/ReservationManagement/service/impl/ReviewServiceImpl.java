package com.picamrr.ReservationManagement.service.impl;

import com.picamrr.ReservationManagement.exception.RestaurantNotFoundException;
import com.picamrr.ReservationManagement.model.Reservation;
import com.picamrr.ReservationManagement.model.Restaurant;
import com.picamrr.ReservationManagement.model.Review;
import com.picamrr.ReservationManagement.model.User;
import com.picamrr.ReservationManagement.repository.ReservationRepository;
import com.picamrr.ReservationManagement.repository.RestaurantRepository;
import com.picamrr.ReservationManagement.repository.ReviewRepository;
import com.picamrr.ReservationManagement.repository.UserRepository;
import com.picamrr.ReservationManagement.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<Review> getReviewsForRestaurant(long id) {
        return reviewRepository
                .findAll()
                .stream()
                .filter(review -> review.getRestaurant().getId() == id)
                .toList();
    }

    @Override
    public Review save(String username, Long restaurantId, Long reservationId, Review review) {
        User user = userRepository.findByEmail(username).get();

        Restaurant restaurant = validateRestaurant(restaurantId);
        Reservation reservation = validateReservation(reservationId);

        Review result = reviewRepository.save(setUpReview(review, restaurant, user));

        updateRestaurantRating(restaurant, result.getNoOfStars());
        updateReservationRating(reservation);
        
        return result;
    }

    private void updateReservationRating(Reservation reservation) {
        reservation.setRated(true);
        reservationRepository.save(reservation);
    }

    private Reservation validateReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RestaurantNotFoundException("Reservation with id " + reservationId + " does not exist!"));
    }

    private void updateRestaurantRating(Restaurant restaurant, int noOfStars) {
        restaurant.setStars(restaurant.getStars()*0.95 + noOfStars*0.05);
        restaurantRepository.save(restaurant);
    }

    private Restaurant validateRestaurant(Long idRestaurant) {
        return restaurantRepository.findById(idRestaurant)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + idRestaurant + " does not exist!"));
    }

    private Review setUpReview(Review review, Restaurant restaurant, User user) {
        review.setUser(user);
        review.setRestaurant(restaurant);
        return review;
    }

}
