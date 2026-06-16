package com.github.Badgaar.service;

import com.github.Badgaar.model.Rental;
import com.github.Badgaar.model.User;
import com.github.Badgaar.model.Vehicle;
import com.github.Badgaar.repository.IRentalRepository;
import com.github.Badgaar.repository.IUserRepository;
import com.github.Badgaar.repository.IVehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentalService implements IRentalService {

    private final IVehicleRepository vehicleRepository;
    private final IRentalRepository rentalRepository;
    private final IUserRepository userRepository;

    public RentalService(IVehicleRepository vehicleRepository, IRentalRepository rentalRepository, IUserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void rentVehicle(String login, String vehicleID) {
        Vehicle vehicle = vehicleRepository.getVehicle(vehicleID);
        if (vehicle == null || vehicle.isRented) {
            return;
        }

        User user = userRepository.getUser(login);
        if (user == null || user.rentedVehicleID != null) {
            return;
        }

        vehicle.setRented(true);
        vehicleRepository.update(vehicle);

        String rentalID = UUID.randomUUID().toString();
        Rental rental = new Rental(rentalID, user, vehicle, true);
        rental.rentDate = LocalDate.now().toString();
        rentalRepository.add(rental);

        user.rentedVehicleID = rentalID;
        userRepository.update(user);
    }

    @Override
    public void returnVehicle(String login) {
        User user = userRepository.getUser(login);
        if (user == null || user.rentedVehicleID == null) {
            return;
        }

        Rental usersRental = rentalRepository.getRental(user.rentedVehicleID);
        if (usersRental == null) {
            return;
        }

        Vehicle vehicle = usersRental.vehicle;
        if (vehicle == null) {
            return;
        }

        vehicle.setRented(false);
        vehicleRepository.update(vehicle);

        user.rentedVehicleID = null;
        userRepository.update(user);

        usersRental.setHasEnded(true);
        usersRental.returnDate = LocalDate.now().toString();
        rentalRepository.update(usersRental);
    }

    @Override
    public Optional<Rental> findActiveRentalByUserId(String id) {
        for (Rental rental : rentalRepository.getRentals()) {
            if (rental.getUserId() != null && rental.getUserId().equals(id) && !rental.hasEnded) {
                return Optional.of(rental);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Rental> findUserRentals(String id) {
        List<Rental> rentals = new ArrayList<>();
        for (Rental rental : rentalRepository.getRentals()) {
            if (rental.getUserId() != null && rental.getUserId().equals(id)) {
                rentals.add(rental);
            }
        }
        return rentals;
    }

    @Override
    public List<Rental> findAllRentals() {
        return new ArrayList<>(rentalRepository.getRentals());
    }

    @Override
    public boolean vehicleHasActiveRental(String vehicleID) {
        for (Rental rental : rentalRepository.getRentals()) {
            if (rental.getVehicleId() != null && rental.getVehicleId().equals(vehicleID) && !rental.hasEnded) {
                return true;
            }
        }
        return false;
    }
}