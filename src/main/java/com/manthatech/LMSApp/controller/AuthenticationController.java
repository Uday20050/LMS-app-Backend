package com.manthatech.LMSApp.controller;

import com.manthatech.LMSApp.dto.LoginDto;
import com.manthatech.LMSApp.model.User;
import com.manthatech.LMSApp.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Autowired
    private UserService userService;

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam String newPassword, @AuthenticationPrincipal UserDetails userDetails) {
        if (newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("newPassword parameter is required");
        }
        User user = userService.findByEmail(userDetails.getUsername());
        if (user != null) {
            user.setPassword(newPassword);
            user.setDefaultPassword(false);
            userService.save(user);
            return ResponseEntity.ok("Password changed successfully");
        }
        return ResponseEntity.badRequest().body("Invalid request");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginDto loginUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
            String jwtToken = jwtService.generateToken((UserDetails) authenticatedUser);
            boolean isDefaultPassword = authenticatedUser.isDefaultPassword();
            String role = authenticatedUser.getRole();
            LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime(), isDefaultPassword, role);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }
}

