package com.picamrr.ReservationManagement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private long id;
    private String name;
    private String address;
    private String phoneNumber;
    private Double stars;

    private String image;

    @OneToMany
    @JoinColumn(name = "restaurant_id")
    private List<AvailableSeats> availableSeatsPerInterval;

    public Restaurant(String name, String address, String phoneNumber, Double stars) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.stars = stars;
    }

}
