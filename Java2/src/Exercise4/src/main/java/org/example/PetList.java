package org.example;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        List<Animal> result = pets.stream()
                .filter(Objects::nonNull)
                .map(pet -> pet.getAge() > 10
                        ? pet.withAge(pet.getAge() + 1)
                        : pet)
                .collect(Collectors.toList());

        result.forEach(System.out::println);

        scanner.close();
    }

    static int readSize(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Could not parse a number. Please, try again");
            return readSize(scanner);
        }
    }

    static List<Animal> readPets(Scanner scanner, int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> readPet(scanner))
                .collect(Collectors.toList());
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
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Could not parse a number. Please, try again");
            return readAge(scanner);
        }
    }
}