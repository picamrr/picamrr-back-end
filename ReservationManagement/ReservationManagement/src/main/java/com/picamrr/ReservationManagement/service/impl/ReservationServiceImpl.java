package com.picamrr.ReservationManagement.service.impl;

import com.picamrr.ReservationManagement.exception.NotEnoughSeatsException;
import com.picamrr.ReservationManagement.exception.RestaurantNotFoundException;
import com.picamrr.ReservationManagement.model.*;
import com.picamrr.ReservationManagement.repository.ReservationRepository;
import com.picamrr.ReservationManagement.repository.RestaurantRepository;
import com.picamrr.ReservationManagement.repository.UserRepository;
import com.picamrr.ReservationManagement.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Reservation save(String userEmail, Long idRestaurant, Reservation reservation) {
        User user = userRepository.findByEmail(userEmail).get();
        Restaurant restaurant = validateRestaurant(idRestaurant);
        validateNoOfSeats(reservation, restaurant);
        return reservationRepository.save(setUpReservation(reservation, restaurant, user));
    }

    @Override
    public List<Reservation> getAllByUser(String userEmail) {
        return reservationRepository.getAllByUser(userRepository.findByEmail(userEmail).get());
    }

    private Restaurant validateRestaurant(Long idRestaurant) {
        return restaurantRepository.findById(idRestaurant)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + idRestaurant + " does not exist!"));
    }

    private void validateNoOfSeats(Reservation reservation, Restaurant restaurant) {
        List<AvailableSeats> availableSeatsList = restaurant.getAvailableSeatsPerInterval();
        availableSeatsList.forEach(availableSeats -> {
            if (availableSeats.getGap().equals(reservation.getGap())) {
                if (reservation.getNoOfSeats() > availableSeats.getNoSeats()) {
                    throw new NotEnoughSeatsException("We have " + availableSeats.getNoSeats() + " seats in that gap! ;(");
                } else {
                    availableSeats.setNoSeats(availableSeats.getNoSeats() - reservation.getNoOfSeats());
                }
            }
        });
    }

    private Reservation setUpReservation(Reservation reservation, Restaurant restaurant, User user) {
        reservation.setReservationMadeAt(LocalDateTime.now());
        reservation.setUser(user);
        reservation.setRestaurant(restaurant);
        return reservation;
    }
}
