package org.example;

import java.util.Scanner;

public class AverageNegativeNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = readSize(scanner);
        if (size <= 0) {
            System.out.println("Input error. Size <= 0");
            return;
        }

        int[] numbers = readNumbers(scanner, size);
        double average = findNegativeAverage(numbers);

        if (average == Double.POSITIVE_INFINITY) {
            System.out.println("There are no negative elements");
        } else {
            System.out.println((int) average);
        }
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
                    System.out.print("Введите число #" + (i+1) + ": ");
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

    static double findNegativeAverage(int[] numbers) {
        int sum = 0;
        int count = 0;

        for (int num : numbers) {
            if (num < 0) {
                sum += num;
                count++;
            }
        }

        return count > 0 ? (double) sum / count : Double.POSITIVE_INFINITY;
    }
}

