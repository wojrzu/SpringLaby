package com.github.Badgaar;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepositoryImpl implements IVehicleRepository{

    String filePath = "/Users/wojciechrzucidlo/Desktop/LAB1/src/main/java/com/github/Badgaar/vehicles.txt";

    public List<Vehicle> vehicles = new ArrayList<>();

    VehicleRepositoryImpl() {
        load();
    }

    @Override
    public void rentVehicle(String vehicleUUID) {
        for (Vehicle v : vehicles) {
            if(v.vehicleUUID.equals(vehicleUUID)) {
                v.isRented = true;
            }
        }
        save();
    }

    @Override
    public void returnVehicle(String vehicleUUID) {
        for (Vehicle v : vehicles) {
            if(v.vehicleUUID.equals(vehicleUUID)){
                v.isRented = false;
            }
        }
        save();
    }

    @Override
    public void load() {
        File file = new File(filePath);

        vehicles.clear();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {

                String[] data = line.split(";");

                String type = data[0];
                String id = data[1];
                String brand = data[2];
                String model = data[3];
                int year = Integer.parseInt(data[4]);
                double price = Double.parseDouble(data[5]);
                boolean rented = Boolean.parseBoolean(data[6]);

                if (type.equals("CAR")) {

                    vehicles.add(new Car(id, brand, model, year, price, rented));

                } else if (type.equals("MOTORCYCLE")) {

                    Categories category = Categories.valueOf(data[7]);

                    vehicles.add(
                            new Motorcycle(id, brand, model, year, price, rented, category)
                    );
                }
            }
            reader.close();
        } catch (IOException e){
        }
    }

    @Override
    public void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for(Vehicle v : vehicles)
                {
                    writer.write(v.toCSV());
                    writer.newLine();
                }
            writer.close();
        } catch (IOException e){
        }
    }

    public void showAvailableVehicles(){
        for (Vehicle v : vehicles) {
            System.out.println(v.toCSV());
        }
    }

    public Boolean isAvailableVehicle(String vehicleUUID){
        for (Vehicle v : vehicles) {
            if(v.vehicleUUID.equals(vehicleUUID)){
                if (v.isRented) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void add(String type, String brand, String model, int year, double price, boolean rented, Categories category){

    }

    @Override
    public void remove(int id){
        for (Vehicle v : vehicles) {
            if(v.vehicleUUID.equals(id)){
                vehicles.remove(v);
            }
        }
    }

    @Override
    public Vehicle getVehicle(int id){
        for (Vehicle v : vehicles) {
            if(v.vehicleUUID.equals(id)){
                return v;
            }
        }
        return null;
    }

    @Override
    public List<Vehicle> getVehicles(){
        List<Vehicle> copyList = new ArrayList<>();
        for(Vehicle v : vehicles){
            copyList.add(v.copy());
        }

        return copyList;
    }
}
