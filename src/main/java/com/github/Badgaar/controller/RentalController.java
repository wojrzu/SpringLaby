package com.github.Badgaar.controller;

import com.github.Badgaar.model.Rental;
import com.github.Badgaar.model.User;
import com.github.Badgaar.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @GetMapping
    public List<Rental> list() {
        return rentalService.findAllRentals();
    }

    @GetMapping("/users/{userId}")
    public List<Rental> userRentals(@PathVariable String userId) {
        return rentalService.findUserRentals(userId);
    }

    @PostMapping("/rent/{vehicleId}")
    public Rental rent(@PathVariable String vehicleId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        rentalService.rentVehicle(user.login, vehicleId);
        return rentalService.findActiveRentalByUserId(user.id).orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Vehicle unavailable"));
    }

    @PostMapping("/return")
    public Rental returnVehicle(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Rental active = rentalService.findActiveRentalByUserId(user.id).orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "No active rental found"));
        rentalService.returnVehicle(user.login);
        return active;
    }

    @GetMapping("/my")
    public List<Rental> myRentals(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return rentalService.findUserRentals(user.id);
    }
}