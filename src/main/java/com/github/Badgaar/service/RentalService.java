package com.github.Badgaar.service;

import com.github.Badgaar.impl.User;
import com.github.Badgaar.impl.Vehicle;
import com.github.Badgaar.repository.IRentalRepository;
import com.github.Badgaar.repository.IUserRepository;
import com.github.Badgaar.repository.IVehicleRepository;
import com.github.Badgaar.impl.Rental;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RentalService {
    private IVehicleRepository vehicleRepository;
    private IRentalRepository rentalRepository;
    private IUserRepository userRepository;

    public RentalService(IVehicleRepository vehicleRepository, IRentalRepository rentalRepository, IUserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }

    public void rentVehicle(String login, String vehicleID) {
        Vehicle vehicle = vehicleRepository.getVehicle(vehicleID);
        if(vehicle == null || vehicle.isRented) {
            return;
        }

        User user = userRepository.getUser(login);
        if(user == null || user.rentedVehicleID != null) {
            return;
        }

        String rentalID = UUID.randomUUID().toString();
        Rental rental = new Rental(rentalID, login, vehicleID, true);
        rentalRepository.add(rental);
        user.rentedVehicleID = rentalID;
        userRepository.update(user);
    }

    public void returnVehicle(String login) {
        User user = userRepository.getUser(login);

        if(user == null || user.rentedVehicleID == null) {
            return;
        }

        Rental usersRental = rentalRepository.getRental(user.rentedVehicleID);
        if(usersRental == null) {
            return;
        }

        user.rentedVehicleID = null;
        rentalRepository.remove(usersRental);

        userRepository.update(user);
        rentalRepository.update(usersRental);
    }

    public Optional<Rental> findActiveRentalByUserId(String id) {
        for (Rental rental : rentalRepository.getRentals()) {
            if(rental.vehicleID.equals(id)) {
                return Optional.of(rental);
            }
        }
        return Optional.empty();
    }

    public List<Rental> findUserRentals(String id) {
        List<Rental> rentals = new ArrayList<>();
        for (Rental rental : rentalRepository.getRentals()) {
            if(rental.vehicleID.equals(id)) {
                rentals.add(rental);
            }
        }
        return rentals;
    }

    public List<Rental> findAllRentals() {
        return new ArrayList<>(rentalRepository.getRentals());
    }

    public boolean vehicleHasActiveRental(String vehicleID) {
        for (Rental rental : rentalRepository.getRentals()) {
            if(rental.vehicleID.equals(vehicleID)) {
                return true;
            }
        }
        return false;
    }
}
