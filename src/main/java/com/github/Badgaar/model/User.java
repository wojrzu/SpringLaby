package com.github.Badgaar.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    public String id;

    @Column(name = "login", unique = true, nullable = false)
    public String login;

    @Column(name = "password_hash", nullable = false)
    public String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    public Role role;

    @Column(name = "rented_vehicle_id")
    public String rentedVehicleID;

    public User(String id, String login, String passwordHash, Role role) {
        this.id = id;
        this.login = login;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public String toCSV() {
        return login + ";" + passwordHash + ";" + role;
    }

    @Override
    public String toString() {
        return ("Login: " + login + ";" + passwordHash + ";" + role + ";" + rentedVehicleID);
    }

    public User copy() {
        return new User(id, login, passwordHash, role);
    }
}