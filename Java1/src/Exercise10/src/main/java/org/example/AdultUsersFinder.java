package org.example;

import java.util.*;

public class AdultUsersFinder {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int size = readSize(scanner);
        if (size <= 0) {
            System.out.println("Input error");
            scanner.close();
            return;
        }

        List<User> users = readUsers(scanner, size);

        List<String> adults = users.stream()
                .filter(user -> user.getAge() >= 18)
                .map(User::getName)
                .toList();

        if (adults.isEmpty()) {
            System.out.println();
        } else {
            for (int i = 0; i < adults.size(); i++) {
                System.out.print(adults.get(i));
                if (i < adults.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }

        scanner.close();
    }

    static int readSize(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Введите количество пользователей: ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Could not parse a number. Please, try again");
            }
        }
    }

    static List<User> readUsers(Scanner scanner, int size) {
        List<User> users = new ArrayList<>();
        System.out.println("Введите данные пользователей (имя + возраст):");

        for (int i = 0; i < size; i++) {
            String name = readName(scanner);
            int age = readAge(scanner);

            if (age > 0) {
                users.add(new User(name, age));
            } else {
                System.out.println("Incorrect input. Age <= 0");
            }
        }
        return users;
    }

    static String readName(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Имя: ");
                return scanner.nextLine().trim();
            } catch (Exception e) {
                System.out.println("Could not parse name. Please, try again");
            }
        }
    }

    static int readAge(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Возраст: ");
                int age = Integer.parseInt(scanner.nextLine());
                return age;
            } catch (NumberFormatException e) {
                System.out.println("Could not parse a number. Please, try again");
            }
        }
    }
}
