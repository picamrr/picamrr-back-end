package com.picamrr.ReservationManagement.service.impl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import com.picamrr.ReservationManagement.model.User;
import com.picamrr.ReservationManagement.repository.UserRepository;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTest {

    private AuthenticationService authenticationService;
    private final UserRepository userRepository = mock(UserRepository.class);
    private final String username = "someUsername";

    @Before
    public void init() {
        authenticationService = new AuthenticationService(userRepository);
    }

    @Test
    public void test_loadUserByUsername_whenUsernameDoesntExist_throwsException() {
        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> authenticationService.loadUserByUsername(username));
    }

    @Test
    public void test_loadUserByUsername_whenUsernameExists_returnsUser() {
        User user = mock(User.class);
        when(user.getEmail()).thenReturn(username);
        String password = "password";
        when(user.getPassword()).thenReturn(password);
        Mockito.when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = authenticationService.loadUserByUsername(username);
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
    }
}