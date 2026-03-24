package com.github.Badgaar;

public class Motorcycle extends Vehicle {
    Categories category;

    public Motorcycle(String vehicleUUID, String brand, String model, Integer year, double price, boolean rented, Categories category) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.isRented = rented;
        this.vehicleUUID = vehicleUUID;
        this.category = category;
    }

    @Override
    public String toCSV() {
        String csvReady = "MOTORCYCLE" + ";" + vehicleUUID + ';' + brand + ';' + model + ';' + year + ';' + price + ';' + isRented + ';' + category;
        return csvReady;
    }

    @Override
    public Vehicle copy() {
        return new Motorcycle(String.valueOf(vehicleUUID), brand, model, year, price, isRented, category);
    }

    @Override
    public void setRented(Boolean rented) {
        this.isRented = rented;
    }


}
