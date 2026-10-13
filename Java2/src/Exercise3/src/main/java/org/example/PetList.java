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

        List<Animal> pets = readPets(scanner, size);

        // Вывод Herbivore
        for (Animal pet : pets) {
            if (pet instanceof Herbivore) {
                System.out.println(pet);
            }
        }

        // Вывод Omnivore
        for (Animal pet : pets) {
            if (pet instanceof Omnivore) {
                System.out.println(pet);
            }
        }

        scanner.close();
    }

    static int readSize(Scanner scanner) {
        try {
            String line = scanner.nextLine().trim();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static List<Animal> readPets(Scanner scanner, int size) {
        List<Animal> pets = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Animal pet = readPet(scanner);
            if (pet != null) {
                pets.add(pet);
            }
        }

        return pets;
    }

    static Animal readPet(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            return null;
        }

        String type = scanner.nextLine().trim().toLowerCase();

        return switch (type) {
            case "dog" -> readDog(scanner);
            case "cat" -> readCat(scanner);
            case "hamster" -> readHamster(scanner);
            case "guinea" -> readGuineaPig(scanner);
            default -> {
                System.out.println("Incorrect input. Unsupported pet type");
                yield null;
            }
        };
    }

    static Dog readDog(Scanner scanner) {
        String name = scanner.nextLine().trim();
        int age = readAge(scanner);

        if (age == -1) {
            return null;
        }

        return new Dog(name, age);
    }

    static Cat readCat(Scanner scanner) {
        String name = scanner.nextLine().trim();
        int age = readAge(scanner);

        if (age == -1) {
            return null;
        }

        return new Cat(name, age);
    }

    static Hamster readHamster(Scanner scanner) {
        String name = scanner.nextLine().trim();
        int age = readAge(scanner);

        if (age == -1) {
            return null;
        }

        return new Hamster(name, age);
    }

    static GuineaPig readGuineaPig(Scanner scanner) {
        String name = scanner.nextLine().trim();
        int age = readAge(scanner);

        if (age == -1) {
            return null;
        }

        return new GuineaPig(name, age);
    }

    static int readAge(Scanner scanner) {
        try {
            String line = scanner.nextLine().trim();
            int age = Integer.parseInt(line);

            if (age <= 0) {
                System.out.println("Incorrect input. Age <= 0");
                return -1;
            }

            return age;
        } catch (NumberFormatException e) {
            System.out.println("Could not parse a number.");
            return -1;
        }
    }
}