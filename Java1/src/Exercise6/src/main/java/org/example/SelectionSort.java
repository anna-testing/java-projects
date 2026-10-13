package org.example;

import java.util.Scanner;

public class SelectionSort {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int size = readSize(scanner);
        if (size <= 0) {
            System.out.println("Input error. Size <= 0");
            scanner.close();
            return;
        }

        double[] numbers = readNumbers(scanner, size);

        selectionSort(numbers);

        printArray(numbers);

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

    static double[] readNumbers(Scanner scanner, int size) {
        double[] numbers = new double[size];
        System.out.println("Введите числа:");

        for (int i = 0; i < size; i++) {
            while (true) {
                try {
                    System.out.print("Число " + (i + 1) + ": ");
                    String input = scanner.nextLine();
                    numbers[i] = Double.parseDouble(input);  // ← double!
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Could not parse a number. Please, try again");
                }
            }
        }
        return numbers;
    }

    static void selectionSort(double[] array) {
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                double temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }
    }

    static void printArray(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%.1f", array[i]);  // ← 1 знак после точки
            if (i < array.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}
