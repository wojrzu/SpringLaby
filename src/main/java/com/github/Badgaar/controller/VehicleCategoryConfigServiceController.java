package com.github.Badgaar.controller;

import com.github.Badgaar.model.VehicleCategoryConfig;
import com.github.Badgaar.service.VehicleCategoryConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class VehicleCategoryConfigServiceController {

    private final VehicleCategoryConfigService configService;

    public VehicleCategoryConfigServiceController(VehicleCategoryConfigService configService) {
        this.configService = configService;
    }

    @GetMapping
    public List<VehicleCategoryConfig> list() {
        return configService.findAllCategories();
    }

    @GetMapping("/{category}")
    public VehicleCategoryConfig get(@PathVariable String category) {
        return configService.getByCategory(category);
    }
}