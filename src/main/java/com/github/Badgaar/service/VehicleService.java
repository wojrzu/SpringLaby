package com.github.Badgaar.service;

import com.github.Badgaar.model.Vehicle;
import com.github.Badgaar.model.VehicleCategoryConfig;
import com.github.Badgaar.repository.IVehicleRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Service
public class VehicleService implements IVehicleService {

    private final IVehicleRepository vehicleRepository;
    private final VehicleValidator vehicleValidator;
    private final VehicleCategoryConfigService configService;

    @Override
    public Vehicle addVehicle(Vehicle vehicle) {
        vehicleValidator.validate(vehicle);
        vehicle.setId(UUID.randomUUID().toString());
        vehicleRepository.add(vehicle);
        return vehicle;
    }

    @Override
    public void removeVehicle(String id) {
        vehicleRepository.remove(id);
    }

    @Override
    public List<VehicleCategoryConfig> getCategories() {
        return configService.findAllCategories();
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        return new ArrayList<>(vehicleRepository.getVehicles());
    }

    @Override
    public boolean isVehicleRented(String id) {
        for (Vehicle v : vehicleRepository.getVehicles()) {
            if (v.id.equals(id) && v.isRented) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Vehicle findById(String id) {
        return vehicleRepository.getVehicle(id);
    }

    @Override
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