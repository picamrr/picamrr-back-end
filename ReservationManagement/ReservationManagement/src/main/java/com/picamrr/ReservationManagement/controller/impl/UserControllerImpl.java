package com.picamrr.ReservationManagement.controller.impl;

import com.picamrr.ReservationManagement.controller.UserController;
import com.picamrr.ReservationManagement.model.User;
import com.picamrr.ReservationManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public ResponseEntity<User> update(String email, User user) {
        return new ResponseEntity<>(userService.update(email, user), HttpStatus.OK);
    }
}
