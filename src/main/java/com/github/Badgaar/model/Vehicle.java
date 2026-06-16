package com.github.Badgaar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Vehicle {

    @Id
    @Column(name = "id")
    @Getter
    public String id;

    @Column(name = "category")
    public String category;

    @Column(name = "brand")
    public String brand;

    @Column(name = "model")
    public String model;

    @Column(name = "year")
    public Integer year;

    @Column(name = "price")
    public Double price;

    @Column(name = "plate")
    public String plate;

    @Column(name = "is_rented")
    public boolean isRented;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attributes")
    public Map<String, Object> attributes = new HashMap<>();

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
