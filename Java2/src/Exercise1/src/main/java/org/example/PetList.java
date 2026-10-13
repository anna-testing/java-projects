package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PetList {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int size = readSize(scanner);
        if (size <= 0) {
            System.out.println("Input error");
            scanner.close();
            return;
        }

        List<Animal> pets = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Animal pet = readPet(scanner);
            if (pet != null) {
                pets.add(pet);
            }
        }

        for (Animal pet : pets) {
            System.out.println(pet);
        }

        scanner.close();
    }

    static int readSize(Scanner scanner) {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                int size = Integer.parseInt(line);
                if (size <= 0) {
                    System.out.println("Input error");
                    continue;
                }
                return size;
            } catch (NumberFormatException e) {
                System.out.println("Input error");
            }
        }
    }

    static Animal readPet(Scanner scanner) {
        String type = scanner.nextLine().trim().toLowerCase();

        return switch (type) {
            case "dog" -> readDog(scanner);
            case "cat" -> readCat(scanner);
            default -> {
                System.out.println("Incorrect input. Unsupported pet type");
                yield null;
            }
        };
    }

    static Dog readDog(Scanner scanner) {
        String name = scanner.nextLine().trim();
        int age = readAge(scanner);

        if (age <= 0) {
            System.out.println("Incorrect input. Age <= 0");
            return null;
        }

        return new Dog(name, age);
    }

    static Cat readCat(Scanner scanner) {
        String name = scanner.nextLine().trim();
        int age = readAge(scanner);

        if (age <= 0) {
            System.out.println("Incorrect input. Age <= 0");
            return null;
        }

        return new Cat(name, age);
    }

    static int readAge(Scanner scanner) {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Could not parse a number");
            }
        }
    }
}
