package com.github.Badgaar.repository;

import com.github.Badgaar.model.VehicleCategoryConfig;

import java.util.List;
import java.util.Optional;

public interface IVehicleCategoryConfigRepository {
    List<VehicleCategoryConfig> findAll();
    Optional<VehicleCategoryConfig> findById(String id);

}
