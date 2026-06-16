package com.github.Badgaar.service;

import com.github.Badgaar.model.Vehicle;
import com.github.Badgaar.model.VehicleCategoryConfig;

import java.util.List;

public interface IVehicleService {
    Vehicle addVehicle(Vehicle vehicle);
    void removeVehicle(String id);
    List<VehicleCategoryConfig> getCategories();
    List<Vehicle> findAllVehicles();
    boolean isVehicleRented(String id);
    Vehicle findById(String id);
    List<Vehicle> findAvailableVehicles();
}