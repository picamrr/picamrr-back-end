package com.picamrr.ReservationManagement.repository;

import com.picamrr.ReservationManagement.model.Reservation;
import com.picamrr.ReservationManagement.model.Restaurant;
import com.picamrr.ReservationManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> getAllByUser(User user);
}
