package com.picamrr.ReservationManagement.service.impl;

import com.picamrr.ReservationManagement.exception.NotEnoughSeatsException;
import com.picamrr.ReservationManagement.exception.RestaurantNotFoundException;
import com.picamrr.ReservationManagement.model.AvailableSeats;
import com.picamrr.ReservationManagement.model.Reservation;
import com.picamrr.ReservationManagement.model.Restaurant;
import com.picamrr.ReservationManagement.model.User;
import com.picamrr.ReservationManagement.repository.ReservationRepository;
import com.picamrr.ReservationManagement.repository.RestaurantRepository;
import com.picamrr.ReservationManagement.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ReservationServiceImplTest {

    private final ReservationRepository reservationRepository = mock(ReservationRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final RestaurantRepository restaurantRepository = mock(RestaurantRepository.class);
    private ReservationServiceImpl reservationService;
    private final Reservation reservation = mock(Reservation.class);
    private final Restaurant restaurant = mock(Restaurant.class);
    private final Long restaurantId = 1L;
    private final User user = mock(User.class);
    private final String userEmail = "useremail";

    @Before
    public void init() {
        reservationService = new ReservationServiceImpl(reservationRepository, userRepository, restaurantRepository);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(user.getEmail()).thenReturn(userEmail);
        when(restaurant.getId()).thenReturn(restaurantId);
    }

    @Test
    public void test_save_whenEnoughSeatsAvailable_shouldReturnAndSaveReservation() {
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        when(reservation.getNoOfSeats()).thenReturn(15);
        when(reservation.getGap()).thenReturn("10-12");
        when(restaurant.getAvailableSeatsPerInterval()).thenReturn(List.of(new AvailableSeats(1, "10-12", 10)));
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(reservation.getRestaurant()).thenReturn(restaurant);

        assertThrows(NotEnoughSeatsException.class, () -> reservationService.save(userEmail, restaurantId, reservation));
    }

    @Test
    public void test_save_whenRestaurantNotFound_shouldThrowException() {
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        when(reservation.getNoOfSeats()).thenReturn(15);
        when(reservation.getGap()).thenReturn("10-12");
        when(restaurant.getAvailableSeatsPerInterval()).thenReturn(List.of(new AvailableSeats(1, "10-12", 10)));
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(reservation.getRestaurant()).thenReturn(restaurant);

        assertThrows(RestaurantNotFoundException.class, () -> reservationService.save(userEmail, restaurantId, reservation));
    }

    @Test
    public void test_save_whenNotEnoughSeatsAvailable_shouldThrowException() {
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        when(reservation.getNoOfSeats()).thenReturn(4);
        when(reservation.getGap()).thenReturn("10-12");
        when(restaurant.getAvailableSeatsPerInterval()).thenReturn(List.of(new AvailableSeats(1, "10-12", 10)));
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(reservation.getRestaurant()).thenReturn(restaurant);

        Reservation reservation1 = reservationService.save(userEmail, restaurantId, reservation);
        assertEquals(restaurant, reservation1.getRestaurant());
    }

    @Test
    public void test_getAllByUser_whenUserHasOneSuccessfulReservation_shouldReturnIt() {
        when(reservationRepository.getAllByUser(user)).thenReturn(List.of(reservation));

        assertEquals(List.of(reservation), reservationService.getAllByUser(userEmail));
    }

}