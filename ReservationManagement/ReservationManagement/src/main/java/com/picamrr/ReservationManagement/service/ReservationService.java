package com.picamrr.ReservationManagement.service;

import com.picamrr.ReservationManagement.model.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation save(String userEmail, Long idRestaurant, Reservation reservation);

    List<Reservation> getAllByUser(String userEmail);
}
