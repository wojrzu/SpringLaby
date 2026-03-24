package com.github.Badgaar;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        VehicleRepositoryImpl repo = new VehicleRepositoryImpl();
        System.out.println("Dostępne pojazdy:");
        repo.showAvailableVehicles();

        System.out.println("Dostępne komendy: R - Wypożycz pojazd, E - Oddaj pojazd, S - Status repozytorium, C - Zamknij konsole");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        while (!line.equals("C")){
            switch (line) {
                case "R":
                    System.out.println("Podaj ID pożądanego pojazdu:");
                    if(repo.isAvailableVehicle(scanner.nextLine()))
                    repo.rentVehicle(scanner.nextLine());
                    System.out.println("Wypożyczono.");
                    repo.showAvailableVehicles();
                    break;
                case "E":
                    System.out.println("Podaj ID zwracanego pojazdu:");
                    repo.returnVehicle(scanner.nextLine());
                    System.out.println("Zwrócono.");
                    repo.showAvailableVehicles();
                    break;
                case "S":
                    System.out.println("Obecny status repozytorium:");
                    repo.showAvailableVehicles();
                    break;
                default:
                    System.out.println("Nieznana komenda.");
            }
            System.out.println("Podaj następną komendę:");
            line = scanner.nextLine();
        }
    }
}