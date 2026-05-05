package com.github.Badgaar.impl;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleCategoryConfig {
    private String category;
    @Getter
    private Map<String, String> attributes = new HashMap<>();

    public void addAttribute(String name, String type) {
        attributes.put(name, type);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public VehicleCategoryConfig copy() {
        return new VehicleCategoryConfig(category, attributes);
    }
}
