package com.picamrr.ReservationManagement.controller;

import com.picamrr.ReservationManagement.model.Restaurant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = {"/restaurants"})
public interface RestaurantController {

    @GetMapping
    ResponseEntity<List<Restaurant>> findAll();
}
