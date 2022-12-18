package com.picamrr.ReservationManagement.controller;

import com.picamrr.ReservationManagement.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin()
@RequestMapping("/users")
public interface UserController {
    @PutMapping("/{email}")
    ResponseEntity<User> update(@PathVariable String email, @Valid @RequestBody User user);
}
