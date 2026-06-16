package com.github.Badgaar.repository;

import com.github.Badgaar.model.Rental;

import java.util.List;

public interface IRentalRepository {
    void add(Rental rental);
    void update(Rental rental);
    List<Rental> getRentals();
    void remove(Rental rental);
    Rental getRental(String rentalID);
}
