package org.example;

import java.util.*;

public class Filtering {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int size = readSize(scanner);
        if (size <= 0) {
            System.out.println("Input error");
            scanner.close();
            return;
        }

        List<String> strings = readStrings(scanner, size);

        System.out.print("Введите подстроку для фильтрации: ");
        String substring = scanner.nextLine().trim();

        List<String> filtered = filterBySubstring(strings, substring);

        if (filtered.isEmpty()) {

        } else {
            for (int i = 0; i < filtered.size(); i++) {
                System.out.print(filtered.get(i));
                if (i < filtered.size() - 1) {
                    System.out.print(", ");
                }
            }
        }
        System.out.println();

        scanner.close();
    }

    static int readSize(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Введите количество строк: ");
                String input = scanner.nextLine();
                int size = Integer.parseInt(input);
                return size;
            } catch (NumberFormatException e) {
                System.out.println("Could not parse a number. Please, try again");
            }
        }
    }

    static List<String> readStrings(Scanner scanner, int size) {
        List<String> strings = new ArrayList<>();
        System.out.println("Введите строки:");

        for (int i = 0; i < size; i++) {
            System.out.print("Строка " + (i + 1) + ": ");
            String line = scanner.nextLine().trim();
            strings.add(line);
        }
        return strings;
    }


    static List<String> filterBySubstring(List<String> strings, String substring) {
        List<String> result = new ArrayList<>();

        for (String str : strings) {
            if (str.toLowerCase().contains(substring.toLowerCase())) {
                result.add(str);
            }
        }
        return result;
    }
}
