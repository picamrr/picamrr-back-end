package com.picamrr.ReservationManagement.service.impl;

import com.picamrr.ReservationManagement.model.Restaurant;
import com.picamrr.ReservationManagement.repository.RestaurantRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestaurantServiceImplTest {

    private RestaurantServiceImpl restaurantService;
    private final RestaurantRepository restaurantRepository = mock(RestaurantRepository.class);

    @Before
    public void init() {
        restaurantService = new RestaurantServiceImpl(restaurantRepository);
    }

    @Test
    public void test_findAll_returnsTheList() {
        Restaurant restaurant = mock(Restaurant.class);
        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));
        assertEquals(1, restaurantService.findAll().size());
    }
}