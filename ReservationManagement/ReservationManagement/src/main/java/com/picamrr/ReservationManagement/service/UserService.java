package com.picamrr.ReservationManagement.service;

import com.picamrr.ReservationManagement.model.User;

import java.util.Optional;

public interface UserService {
    User update(String email, User user);
}
