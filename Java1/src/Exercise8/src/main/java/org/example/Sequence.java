package org.example;

import java.util.Scanner;

public class Sequence {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите числа (разделённые пробелами): ");
        String inputLine = scanner.nextLine();
        Scanner lineScanner = new Scanner(inputLine);

        int firstNumber = 0;
        int prevNumber = Integer.MIN_VALUE;
        int position = 1;
        boolean hasNumbers = false;
        boolean foundFirst = false;

        while (lineScanner.hasNext()) {
            try {
                int currentNumber = Integer.parseInt(lineScanner.next());
                hasNumbers = true;

                if (!foundFirst) {
                    firstNumber = currentNumber;
                    foundFirst = true;
                    prevNumber = currentNumber;
                    position = 2;
                    continue;
                }

                if (currentNumber < prevNumber) {
                    System.out.println("The sequence is not ordered from the ordinal number of the number " + (position - 1));
                    lineScanner.close();
                    scanner.close();
                    return;
                }

                prevNumber = currentNumber;
                position++;

            } catch (NumberFormatException e) {
                break;
            }
        }

        lineScanner.close();
        scanner.close();

        if (!hasNumbers) {
            System.out.println("Input error");
        } else {
            System.out.println("The sequence is ordered in ascending order");
        }
    }
}
