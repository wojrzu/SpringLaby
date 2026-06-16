package com.github.Badgaar.service;

import com.github.Badgaar.model.Rental;

import java.util.List;
import java.util.Optional;

public interface IRentalService {
    void rentVehicle(String login, String vehicleID);
    void returnVehicle(String login);
    Optional<Rental> findActiveRentalByUserId(String id);
    List<Rental> findUserRentals(String id);
    List<Rental> findAllRentals();
    boolean vehicleHasActiveRental(String vehicleID);
}