package com.picamrr.ReservationManagement.service.impl;

import com.picamrr.ReservationManagement.exception.NotEnoughSeatsException;
import com.picamrr.ReservationManagement.exception.ReservationNotFoundException;
import com.picamrr.ReservationManagement.exception.RestaurantNotFoundException;
import com.picamrr.ReservationManagement.model.*;
import com.picamrr.ReservationManagement.repository.ReservationRepository;
import com.picamrr.ReservationManagement.repository.RestaurantRepository;
import com.picamrr.ReservationManagement.repository.UserRepository;
import com.picamrr.ReservationManagement.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private static final String EMAIL_SUBJECT = " \uD83D\uDD14 Ready to have a fun time out? Your reservation is confirmed!";
    private static final String EMAIL_BODY = "Hello, %s\n Thank you for choosing PICAMRR!\n\n Your reservation at %s on %s in the interval %s for %s people is confirmed. " +
            "Free cancellation is available up to one day before your reservation date.\n\n" +
            "Restaurant info:\n" +
            "\uD83D\uDCCD  Address: %s\n" +
            "☎️Phone number: %s\n\n" +
            "Don't forget to rate the restaurant on our app! ⭐\n\n" +
            "Have a great time!";

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final EmailSenderService emailSenderService;

    @Override
    public Reservation save(String userEmail, Long idRestaurant, Reservation reservation) {
        User user = userRepository.findByEmail(userEmail).get();
        Restaurant restaurant = validateRestaurant(idRestaurant);
        validateNoOfSeats(reservation, restaurant);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        var response = reservationRepository.save(setUpReservation(reservation, restaurant, user));
        String message = String.format(EMAIL_BODY, user.getName(), restaurant.getName(), dateFormat.format(reservation.getDateOfReservation()),
                reservation.getGap(), reservation.getNoOfSeats(), restaurant.getAddress(), restaurant.getPhoneNumber());
        emailSenderService.sendEmail(userEmail, EMAIL_SUBJECT, message);
        return response;
    }

    @Override
    public List<Reservation> getAllByUser(String userEmail) {
        return reservationRepository.getAllByUser(userRepository.findByEmail(userEmail).get());
    }

    @Override
    public void delete(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation with id " + reservationId + " does not exist!"));
        if (reservation.getDateOfReservation().before(new java.util.Date())) {
            throw new IllegalArgumentException("A previous reservation cannot be cancelled");
        }
        updateNoOfAvailableSeats(reservation);
        reservationRepository.delete(reservation);
    }

    private Restaurant validateRestaurant(Long idRestaurant) {
        return restaurantRepository.findById(idRestaurant)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + idRestaurant + " does not exist!"));
    }

    private void updateNoOfAvailableSeats(Reservation reservation) {
        List<AvailableSeats> availableSeatsList = reservation.getRestaurant().getAvailableSeatsPerInterval();
        availableSeatsList.forEach(availableSeats -> {
            if (availableSeats.getGap().equals(reservation.getGap())) {
                availableSeats.setNoSeats(availableSeats.getNoSeats() + reservation.getNoOfSeats());
            }
        });
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
