package com.github.Badgaar.repository;

import com.github.Badgaar.impl.Vehicle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VehicleRepository implements IVehicleRepository {

    String vehiclesPath = "vehicles.json";

    public List<Vehicle> vehicles = new ArrayList<>();
    private Gson gson = new GsonBuilder().create();

    public VehicleRepository() {
        load();
    }

    @Override
    public void rentVehicle(String vehicleUUID) {
        for (Vehicle v : vehicles) {
            if(v.id.equals(vehicleUUID)) {
                v.isRented = true;
            }
        }
        save();
    }

    @Override
    public void returnVehicle(String vehicleUUID) {
        for (Vehicle v : vehicles) {
            if(v.id.equals(vehicleUUID)){
                v.isRented = false;
            }
        }
        save();
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
    }

    @Override
    public Vehicle getVehicle(String id){
        for (Vehicle v : vehicles) {
            if(v.id.equals(id)){
                return v.copy();
            }
        }
        return null;
    }

    @Override
    public List<Vehicle> getVehicles(){
        List<Vehicle> copyList = new ArrayList<>();
        for(Vehicle v : vehicles){
            copyList.add(v.copy());
        }

        return copyList;
    }
}
