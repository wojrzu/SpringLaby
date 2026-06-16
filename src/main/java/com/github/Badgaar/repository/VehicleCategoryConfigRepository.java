package com.github.Badgaar.repository;

import com.github.Badgaar.model.VehicleCategoryConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class VehicleCategoryConfigRepository implements IVehicleCategoryConfigRepository {

    private String categoriesPath = "categories.json";
    private List<VehicleCategoryConfig> configs = new ArrayList<>();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public VehicleCategoryConfigRepository() {
        load();
    }

    private void load() {
        File file = new File(categoriesPath);
        configs.clear();
        if (!file.exists()) return;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            VehicleCategoryConfig[] loaded = gson.fromJson(reader, VehicleCategoryConfig[].class);
            reader.close();
            if (loaded != null) {
                configs.addAll(Arrays.asList(loaded));
            }
        } catch (IOException e) {
        }
    }

    @Override
    public List<VehicleCategoryConfig> findAll() {
        List<VehicleCategoryConfig> copy = new ArrayList<>();
        for (VehicleCategoryConfig c : configs) {
            copy.add(c.copy());
        }
        return copy;
    }

    public Optional<VehicleCategoryConfig> findByCategory(String category) {
        return configs.stream()
                .filter(c -> c.getCategory() != null)
                .filter(c -> c.getCategory().equalsIgnoreCase(category))
                .findFirst()
                .map(VehicleCategoryConfig::copy);
    }

    @Override
    public Optional<VehicleCategoryConfig> findById(String id) {
        for (VehicleCategoryConfig c : configs) {
            if (c.getCategory().equals(id)) {
                return Optional.of(c.copy());
            }
        }
        return Optional.empty();
    }
}