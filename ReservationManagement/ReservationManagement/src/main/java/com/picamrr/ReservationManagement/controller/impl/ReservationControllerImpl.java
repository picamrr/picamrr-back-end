package com.picamrr.ReservationManagement.controller.impl;

import com.picamrr.ReservationManagement.controller.ReservationController;
import com.picamrr.ReservationManagement.model.Reservation;
import com.picamrr.ReservationManagement.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationControllerImpl implements ReservationController {
    private final ReservationService reservationService;

    @Override
    public ResponseEntity<Reservation> save(UserDetails user, Long restaurantId, Reservation reservation) {
        return new ResponseEntity<>(reservationService.save(user.getUsername(), restaurantId, reservation), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Reservation>> getAll(UserDetails user) {
        return new ResponseEntity<>(reservationService.getAllByUser(user.getUsername()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> cancelReservation(Long reservationId) {
        reservationService.delete(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
