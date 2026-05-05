package com.github.Badgaar.impl;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    public String login;
    public String id;
    public String passwordHash;
    public Role role;
    public String rentedVehicleID;

    public User(String id, String login, String passwordHash, Role role) {
        this.role = role;
        this.login = login;
        this.passwordHash = passwordHash;
        this.id = id;
    }

    public String toCSV(){
        return login + ";" + passwordHash + ";" + role;
    }

    @Override
    public String toString() {
        return ("Login: " + login + ";" + passwordHash + ";" + role + ";" + rentedVehicleID);
    }

    public User copy(){
        return new User(id, login, passwordHash, role);
    }
}
