package com.github.Badgaar;

import java.util.List;

public interface IVehicleRepository {
    void rentVehicle(String vehicleUUID);
    void returnVehicle(String vehicleUUID);
    List<Vehicle> getVehicles();
    void save();
    void load();
    void add(String type, String brand, String model, int year, double price, boolean rented, Categories category);
    void remove(int id);
    Vehicle getVehicle(int id);
}
