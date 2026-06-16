package com.github.Badgaar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "rental")
public class Rental {

    @Id
    @Column(name = "id")
    public String rentalID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id", nullable = false)
    public Vehicle vehicle;

    @Column(name = "is_occupied")
    public Boolean isOccupied;

    @Column(name = "has_ended")
    public Boolean hasEnded;

    @Column(name = "rent_date")
    public String rentDate;

    @Column(name = "return_date")
    public String returnDate;

    public Rental(String rentalID, User user, Vehicle vehicle, Boolean isOccupied) {
        this.rentalID = rentalID;
        this.user = user;
        this.vehicle = vehicle;
        this.isOccupied = isOccupied;
        this.hasEnded = false;
    }

    public String getUserId() {
        return user != null ? user.id : null;
    }

    public String getVehicleId() {
        return vehicle != null ? vehicle.id : null;
    }
}