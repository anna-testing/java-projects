package org.example;

import java.util.Scanner;

public class TheSameNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int size = readSize(scanner);
        if (size <= 0) {
            System.out.println("Input error. Size <= 0");
            scanner.close();
            return;
        }

        int[] numbers = readNumbers(scanner, size);

        int[] matchingNumbers = findMatchingDigitsNumbers(numbers);

        if (matchingNumbers.length == 0) {
            System.out.println("There are no such elements");
        } else {
            for (int i = 0; i < matchingNumbers.length; i++) {
                System.out.print(matchingNumbers[i]);
                if (i < matchingNumbers.length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        scanner.close();
    }

    static int readSize(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Введите количество чисел: ");
                String input = scanner.nextLine();
                int size = Integer.parseInt(input);
                return size;
            } catch (NumberFormatException e) {
                System.out.println("Could not parse a number. Please, try again");
            }
        }
    }

    static int[] readNumbers(Scanner scanner, int size) {
        int[] numbers = new int[size];
        for (int i = 0; i < size; i++) {
            while (true) {
                try {
                    System.out.print("Введите число " + (i + 1) + ": ");
                    String input = scanner.nextLine();
                    numbers[i] = Integer.parseInt(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Could not parse a number. Please, try again");
                }
            }
        }
        return numbers;
    }

    static int[] findMatchingDigitsNumbers(int[] numbers) {
        int count = 0;
        for (int num : numbers) {
            if (hasSameFirstLastDigit(num)) {
                count++;
            }
        }

        int[] result = new int[count];
        int index = 0;
        for (int num : numbers) {
            if (hasSameFirstLastDigit(num)) {
                result[index++] = num;
            }
        }
        return result;
    }

    static boolean hasSameFirstLastDigit(int number) {
        // Последняя цифра: number % 10
        int lastDigit = Math.abs(number) % 10;  // abs для отрицательных [web:7]

        // Первая цифра: делим до одной цифры
        int firstDigit = Math.abs(number);
        if (firstDigit == 0) {
            return true;
        }
        while (firstDigit >= 10) {
            firstDigit /= 10;
        }

        return firstDigit == lastDigit;
    }
}
