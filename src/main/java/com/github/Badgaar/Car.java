package com.github.Badgaar;

public class Car extends Vehicle {
    public Car(String vehicleUUID, String brand, String model, Integer year, Double price, Boolean rented) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.isRented = rented;
        this.vehicleUUID = vehicleUUID;
    }

    @Override
    public String toCSV() {
        String csvReady = "CAR" + ";" + vehicleUUID + ';' + brand + ';' + model + ';' + year + ';' + price + ';' + isRented;
        return csvReady;
    }

    @Override
    public Vehicle copy() {
        return new Car(String.valueOf(vehicleUUID), brand, model, year, price, isRented);
    }

    @Override
    public void setRented(Boolean rented) {
        this.isRented = rented;
    }


}
