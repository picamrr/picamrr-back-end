package com.picamrr.ReservationManagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;

@Entity
@Table(name = "reservations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;
    private LocalDateTime reservationMadeAt;
    @JsonFormat(pattern = "YYYY-MM-dd")
    @Temporal(DATE)
    private Date dateOfReservation;
    private int noOfSeats;
    private String gap;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Reservation(Date dateOfReservation, int noOfSeats, String gap) {
        this.dateOfReservation = dateOfReservation;
        this.noOfSeats = noOfSeats;
        this.gap = gap;
    }
}
