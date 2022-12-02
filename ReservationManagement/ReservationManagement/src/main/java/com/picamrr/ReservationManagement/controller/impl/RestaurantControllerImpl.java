package com.picamrr.ReservationManagement.controller.impl;

import com.picamrr.ReservationManagement.controller.RestaurantController;
import com.picamrr.ReservationManagement.model.Restaurant;
import com.picamrr.ReservationManagement.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestaurantControllerImpl implements RestaurantController {
    private final RestaurantService restaurantService;

    @Override
    public ResponseEntity<List<Restaurant>> findAll() {
        return new ResponseEntity<>(restaurantService.findAll(), HttpStatus.OK);
    }
}
