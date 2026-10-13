package org.example;

import java.util.Scanner;

public class FinderFibonacci {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = inputNumber(scanner);
            String result = calculateFibonacci(n);
            output(result);
        }
    }

    public static int inputNumber(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter Fibonacci number index: ");
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    throw new NumberFormatException();
                }
                int number = Integer.parseInt(line);
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Could not parse a number. Please, try again");
            }
        }
    }

    public static String calculateFibonacci(int n) {
        if (n < 0) {
            return "Incorrect input";
        }
        if (n > 46) {
            return "Too large n";
        }
        long result = fibonacciRecursive(n);
        return String.valueOf(result);
    }

    public static long fibonacciRecursive(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    public static void output(String result) {
        System.out.println(result);
    }
}
