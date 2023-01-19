package com.picamrr.ReservationManagement.controller;

import com.picamrr.ReservationManagement.model.Reservation;
import com.picamrr.ReservationManagement.model.Review;
import com.picamrr.ReservationManagement.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RequestMapping(path = {"/reviews"})
public interface ReviewController {

    @PostMapping
    ResponseEntity<Review> save(@AuthenticationPrincipal UserDetails user,
                                @RequestParam Long restaurantId,
                                @RequestParam Long reservationId,
                                @RequestBody Review review);


    @GetMapping("/{restaurantId}")
    ResponseEntity<List<Review>> getReviewsForRestaurant(@PathVariable String restaurantId);

}
