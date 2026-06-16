package com.github.Badgaar.repository;

import com.github.Badgaar.model.Vehicle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@Profile("json")
public class VehicleRepository implements IVehicleRepository {

    String vehiclesPath = "vehicles.json";

    public List<Vehicle> vehicles = new ArrayList<>();
    private Gson gson = new GsonBuilder().create();

    public VehicleRepository() {
        load();
    }

    private void load() {
        File file = new File(vehiclesPath);
        if (!file.exists()) {
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Vehicle[] loaded = gson.fromJson(reader, Vehicle[].class);
            reader.close();
            if (loaded != null) {
                vehicles.addAll(Arrays.asList(loaded));
            }
        } catch (IOException e) {
        }
    }

    private void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(vehiclesPath));
            gson.toJson(vehicles, writer);
            writer.close();
        } catch (IOException e) {
        }
    }

    public void showAvailableVehicles(){
        for (Vehicle v : vehicles) {
            System.out.println(v.toCSV());
        }
    }

    public Boolean isAvailableVehicle(String vehicleUUID){
        for (Vehicle v : vehicles) {
            if(v.id.equals(vehicleUUID)){
                return !v.isRented;
            }
        }
        return false;
    }

    @Override
    public void add(Vehicle vehicle) {
        vehicles.add(vehicle);
        save();
    }

    @Override
    public void remove(String id){
        vehicles.removeIf(v -> v.id.equals(id));
        save();
    }

    @Override
    public Vehicle getVehicle(String id){
        for (Vehicle v : vehicles) {
            if(v.id.equals(id)){
                return v;
            }
        }
        return null;
    }

    @Override
    public List<Vehicle> getVehicles(){

        return new ArrayList<>(vehicles);
    }

    @Override
    public void update(Vehicle vehicle) {
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).id.equals(vehicle.id)) {
                vehicles.set(i, vehicle);
                save();
                return;
            }
        }
        return;
    }
}
