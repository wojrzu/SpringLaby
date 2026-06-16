package com.github.Badgaar.service;

import com.github.Badgaar.model.VehicleCategoryConfig;
import com.github.Badgaar.repository.IVehicleCategoryConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleCategoryConfigService {

    private final IVehicleCategoryConfigRepository configRepository;

    public VehicleCategoryConfigService(IVehicleCategoryConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public VehicleCategoryConfig getByCategory(String category) {
        return configRepository.findById(category)
                .orElseThrow(() -> new IllegalArgumentException("Nieznana kategoria pojazdu: " + category));
    }

    public List<VehicleCategoryConfig> findAllCategories() {
        return configRepository.findAll();
    }

    public boolean categoryExists(String category) {
        return configRepository.findById(category).isPresent();
    }
}