package com.github.Badgaar;

import com.github.Badgaar.impl.UI;
import com.github.Badgaar.repository.IVehicleCategoryConfigRepository;
import com.github.Badgaar.repository.IRentalRepository;
import com.github.Badgaar.repository.IUserRepository;
import com.github.Badgaar.repository.IVehicleRepository;
import com.github.Badgaar.repository.RentalRepository;
import com.github.Badgaar.repository.UserRepository;
import com.github.Badgaar.repository.VehicleCategoryConfigRepository;
import com.github.Badgaar.repository.VehicleRepository;
import com.github.Badgaar.service.AuthService;
import com.github.Badgaar.service.RentalService;
import com.github.Badgaar.service.UserService;
import com.github.Badgaar.service.VehicleCategoryConfigService;
import com.github.Badgaar.service.VehicleService;
import com.github.Badgaar.service.VehicleValidator;

public class Main {
    public static void main(String[] args) {
        IVehicleRepository vehicleRepository = new VehicleRepository();
        IUserRepository userRepository = new UserRepository();
        IRentalRepository rentalRepository = new RentalRepository();
        IVehicleCategoryConfigRepository configRepository = new VehicleCategoryConfigRepository();

        VehicleCategoryConfigService configService = new VehicleCategoryConfigService(configRepository);
        VehicleValidator vehicleValidator = new VehicleValidator(configService);

        AuthService authService = new AuthService(userRepository);
        VehicleService vehicleService = new VehicleService(vehicleRepository, vehicleValidator, configService);
        RentalService rentalService = new RentalService(vehicleRepository, rentalRepository, userRepository);
        UserService userService = new UserService(userRepository, rentalRepository);
        VehicleCategoryConfigService categoryConfigService = new VehicleCategoryConfigService(configRepository);

        new UI(authService, vehicleService, rentalService, userService, categoryConfigService).start();
    }
}