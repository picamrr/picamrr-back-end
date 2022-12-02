package com.picamrr.ReservationManagement.repository;

import com.picamrr.ReservationManagement.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
