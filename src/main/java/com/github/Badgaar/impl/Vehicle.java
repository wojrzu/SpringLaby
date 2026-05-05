package com.github.Badgaar.impl;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vehicle {
    @Getter
    public String id;
    public String category;
    public String brand;
    public String model;
    public Integer year;
    public Double price;
    public String plate;
    public boolean isRented;
    public Map<String, Object> attributes;

    public Vehicle copy() {
        return new Vehicle(id, category, brand, model, year, price, plate, isRented, attributes);
    }

    public void addAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    //TODO
    public String toCSV(){
        return "";
    }
}
