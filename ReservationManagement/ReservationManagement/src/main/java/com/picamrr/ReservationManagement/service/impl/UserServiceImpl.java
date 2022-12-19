package com.picamrr.ReservationManagement.service.impl;

import com.picamrr.ReservationManagement.exception.EmptyFieldException;
import com.picamrr.ReservationManagement.exception.InvalidPhoneNumberException;
import com.picamrr.ReservationManagement.exception.UniqueEmailException;
import com.picamrr.ReservationManagement.exception.UserNotFoundException;
import com.picamrr.ReservationManagement.model.User;
import com.picamrr.ReservationManagement.repository.UserRepository;
import com.picamrr.ReservationManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User update(String email, User user) {
        User oldUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " wasn't found!"));
        if (user.getEmail() != null) {
            validateUserEmail(user.getEmail());
            oldUser.setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            validateUserName(user.getName());
            oldUser.setName(user.getName());
        }
        if (user.getPhoneNumber() != null) {
            validateUserPhoneNumber(user.getPhoneNumber());
            oldUser.setPhoneNumber(user.getPhoneNumber());
        }
        return userRepository.save(oldUser);
    }

    @Override
    public User getByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " wasn't found!"));
    }

    private void validateUserEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UniqueEmailException("This email is taken!");
        }
        if (email.isBlank()) {
            throw new EmptyFieldException("Email cannot be empty!");
        }
    }

    private void validateUserName(String name) {
        if (name.isBlank()) {
            throw new EmptyFieldException("Name cannot be empty!");
        }
    }

    private void validateUserPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()) {
            throw new InvalidPhoneNumberException("Phone Number must have 10 digits!");
        }
    }

}
