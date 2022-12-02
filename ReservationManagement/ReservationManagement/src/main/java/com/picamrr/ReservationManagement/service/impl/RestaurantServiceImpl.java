package com.picamrr.ReservationManagement.service.impl;

import com.picamrr.ReservationManagement.model.Restaurant;
import com.picamrr.ReservationManagement.repository.RestaurantRepository;
import com.picamrr.ReservationManagement.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }
}
