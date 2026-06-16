package com.github.Badgaar.repository;

import com.github.Badgaar.model.Rental;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@Profile("json")
public class RentalRepository implements IRentalRepository {

    private String rentalsPath = "rentals.json";
    private List<Rental> rentals = new ArrayList<>();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public RentalRepository() {
        load();
    }

    private void load() {
        File file = new File(rentalsPath);
        rentals.clear();
        if (!file.exists()) return;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Rental[] loaded = gson.fromJson(reader, Rental[].class);
            reader.close();
            if (loaded != null) {
                rentals.addAll(Arrays.asList(loaded));
            }
        } catch (IOException e) {
        }
    }

    private void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(rentalsPath));
            gson.toJson(rentals, writer);
            writer.close();
        } catch (IOException e) {
        }
    }

    @Override
    public void add(Rental rental) {
        rentals.add(rental);
        save();
    }

    @Override
    public void update(Rental rental) {
        for (int i = 0; i < rentals.size(); i++) {
            if (rentals.get(i).rentalID.equals(rental.rentalID)) {
                rentals.set(i, rental);
                save();
                return;
            }
        }
    }

    @Override
    public List<Rental> getRentals() {
        return new ArrayList<>(rentals);
    }

    @Override
    public void remove(Rental rental) {
        rentals.remove(rental);
        save();
    }

    @Override
    public Rental getRental(String rentalID) {
        for (Rental rental : rentals) {
            if (rental.rentalID.equals(rentalID)) {
                return rental;
            }
        }
        return null;
    }
}