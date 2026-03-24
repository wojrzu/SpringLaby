package com.github.Badgaar;

public abstract class Vehicle {
    String brand;
    String model;
    Integer year;
    Double price;
    Boolean isRented;
    String vehicleUUID;

    public abstract String toCSV();

    @Override
    public String toString() {
        return "Vehicle{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", isRented=" + isRented +
                ", vehicleUUID='" + vehicleUUID + '\'' +
                '}';
    }

    public abstract Vehicle copy();
    public void setRented(Boolean rented){
        this.isRented = rented;
    }
    public Boolean isRented() {
        return isRented;
    }
}
