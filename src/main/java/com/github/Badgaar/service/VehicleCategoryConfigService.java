package com.github.Badgaar.service;

import com.github.Badgaar.impl.VehicleCategoryConfig;
import com.github.Badgaar.repository.IVehicleCategoryConfigRepository;

import java.util.List;

public class VehicleCategoryConfigService {

    private final IVehicleCategoryConfigRepository configRepository;

    public VehicleCategoryConfigService(IVehicleCategoryConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public List<VehicleCategoryConfig> findAllCategories() {
        return configRepository.findAll();
    }

    public VehicleCategoryConfig getByCategory(String category) {
        return configRepository.findById(category)
                .orElseThrow(() -> new IllegalArgumentException("Nieznana kategoria pojazdu: " + category));
    }

    public boolean categoryExists(String category) {
        return configRepository.findById(category).isPresent();
    }
}