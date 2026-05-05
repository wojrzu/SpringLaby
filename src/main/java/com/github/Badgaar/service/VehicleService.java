package com.github.Badgaar.service;

import com.github.Badgaar.impl.Vehicle;
import com.github.Badgaar.impl.VehicleCategoryConfig;
import com.github.Badgaar.repository.IVehicleRepository;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class VehicleService {
    private final IVehicleRepository vehicleRepository;
    private final VehicleValidator vehicleValidator;
    private final VehicleCategoryConfigService configService;

    public Vehicle addVehicle(Vehicle vehicle) {
        vehicleValidator.validate(vehicle);
        vehicleRepository.add(vehicle);
        return vehicle;
    }

    public void removeVehicle(String id) {
        Vehicle v = vehicleRepository.getVehicle(id);
        /*if (v == null || v.isRented) {
            return;
        }*/

        vehicleRepository.remove(id);
    }

    public List<VehicleCategoryConfig> getCategories() {
        return configService.findAllCategories();
    }

    public List<Vehicle> findAllVehicles() {
        return new ArrayList<>(vehicleRepository.getVehicles());
    }

    public boolean isVehicleRented(String id) {
        for (Vehicle v : vehicleRepository.getVehicles()) {
            if (v.id.equals(id) && v.isRented) {
                return true;
            }
        }
        return false;
    }

    public Vehicle findById(String id) {
        return vehicleRepository.getVehicle(id);
    }

    public List<Vehicle> findAvailableVehicles() {
        List<Vehicle> available = new ArrayList<>();
        for (Vehicle v : vehicleRepository.getVehicles()) {
            if (!v.isRented) {
                available.add(v);
            }
        }
        return available;
    }
}