package com.manthatech.LMSApp.service;

import com.manthatech.LMSApp.dto.LoginDto;
import com.manthatech.LMSApp.model.User;
import com.manthatech.LMSApp.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    public User authenticate(LoginDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }
}
