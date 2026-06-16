package com.github.Badgaar.repository;

import com.github.Badgaar.model.Vehicle;

import java.util.List;

public interface IVehicleRepository {
    List<Vehicle> getVehicles();
    void add(Vehicle vehicle);
    void remove(String id);
    Vehicle getVehicle(String id);
    void update(Vehicle vehicle);
}
