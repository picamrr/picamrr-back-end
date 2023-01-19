package com.picamrr.ReservationManagement.repository;

import com.picamrr.ReservationManagement.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
