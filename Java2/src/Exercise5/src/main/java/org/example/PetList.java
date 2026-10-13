package org.example;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PetList {

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        long programStartTime = System.currentTimeMillis();

        int size = readSize(scanner);
        if (size <= 0) {
            System.out.println("Input error");
            scanner.close();
            return;
        }

        List<Animal> pets = readPets(scanner, size);

        ExecutorService executor = Executors.newFixedThreadPool(pets.size());

        List<Future<WalkResult>> futures = pets.stream()
                .map(pet -> executor.submit(() -> {
                    double walkDuration = getWalkDuration(pet);
                    long walkStartTime = System.currentTimeMillis();
                    double startTime = (walkStartTime - programStartTime) / 1000.0;

                    double endTime = pet.goToWalk(programStartTime);

                    return new WalkResult(pet, startTime, endTime, walkDuration);
                }))
                .collect(Collectors.toList());

        List<WalkResult> results = new ArrayList<>();
        for (Future<WalkResult> future : futures) {
            try {
                results.add(future.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        results.stream()
                .sorted(Comparator.comparingDouble(WalkResult::duration))
                .forEach(System.out::println);

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        scanner.close();
    }

    static double getWalkDuration(Animal pet) {
        return pet.getAge() * (pet instanceof Dog ? 0.5 : 0.25);
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
                .filter(Objects::nonNull)
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

    record WalkResult(Animal pet, double startTime, double endTime, double duration) {
        @Override
        public String toString() {
            return String.format("%s, start time = %.2f, end time = %.2f",
                    pet, startTime, endTime);
        }
    }
}