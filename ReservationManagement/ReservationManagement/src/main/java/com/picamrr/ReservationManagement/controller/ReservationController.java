package com.picamrr.ReservationManagement.controller;

import com.picamrr.ReservationManagement.model.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RequestMapping(path = {"/reservations"})
public interface ReservationController {
    @PostMapping
    ResponseEntity<Reservation> save(@AuthenticationPrincipal UserDetails user, @RequestParam Long restaurantId, @RequestBody Reservation reservation);

    @GetMapping
    ResponseEntity<List<Reservation>> getAll(@AuthenticationPrincipal UserDetails user);
}
