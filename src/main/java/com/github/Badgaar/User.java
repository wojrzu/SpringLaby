package com.github.Badgaar;

public class User {
    String login;
    String password;
    Role role;
    int rentedVehicleID;

    User() {
        role = Role.USER;
    }

    boolean login(String login, String password) {
        if (login.equals(this.login) && password.equals(this.password)) {
            return true;
        } else {
            return false;
        }
    }

    void rentVehicle(int vehicleID) {
        this.rentedVehicleID = vehicleID;
    }

    void giveBackVehicle(int vehicleID) {
        this.rentedVehicleID = -1;
    }

    void showDetails() {
        System.out.println("Login: " + login);
        System.out.println("Rented Vehicle ID: " + rentedVehicleID);
    }
}
