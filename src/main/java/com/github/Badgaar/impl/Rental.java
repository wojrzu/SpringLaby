package com.github.Badgaar.impl;

import lombok.Getter;

@Getter
public class Rental {

    public String rentalID;
    public String userLogin;
    public String vehicleID;
    public Boolean isOccupied;

    public Rental(String rentalID, String userLogin, String vehicleID, Boolean isOccupied) {
        this.rentalID = rentalID;
        this.userLogin = userLogin;
        this.vehicleID = vehicleID;
        this.isOccupied = isOccupied;
    }

    public String getVehicleId() {
        return vehicleID;
    }

    public String getUserId() {
        return userLogin;
    }
}
