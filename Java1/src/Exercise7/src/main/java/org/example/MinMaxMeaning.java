package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class MinMaxMeaning {
    public static void main(String[] args) {
        Scanner consoleScanner = new Scanner(System.in);
        System.out.print("Введите путь к файлу: ");
        if (!consoleScanner.hasNext()) {
            return;
        }
        String filePath = consoleScanner.next();
        consoleScanner.close();

        File inputFile = new File(filePath);

        if (!inputFile.exists()) {
            System.out.println("Input error. File doesn't exist");
            return;
        }

        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(inputFile);
            fileScanner.useLocale(Locale.US);

            if (!fileScanner.hasNextInt()) {

                System.out.println("Input error. Size <= 0");
                return;
            }

            int count = fileScanner.nextInt();

            if (count <= 0) {
                System.out.println("Input error. Size <= 0");
                return;
            }

            double[] numbers = new double[count];
            int actualReadCount = 0;

            while (actualReadCount < count && fileScanner.hasNext()) {
                if (fileScanner.hasNextDouble()) {
                    numbers[actualReadCount] = fileScanner.nextDouble();
                    actualReadCount++;
                } else {
                    fileScanner.next();
                }
            }

            if (actualReadCount < count) {
                System.out.println("Input error. Insufficient number of elements");
                return;
            }

            double min = numbers[0];
            double max = numbers[0];

            for (int i = 1; i < count; i++) {
                if (numbers[i] < min) {
                    min = numbers[i];
                }
                if (numbers[i] > max) {
                    max = numbers[i];
                }
            }

            System.out.println(count);
            for (int i = 0; i < count; i++) {
                System.out.print(numbers[i]);
                if (i < count - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
            System.out.println("Saving min and max values in file");

            try (PrintWriter writer = new PrintWriter("result.txt")) {
                writer.print(min);
                writer.print(" ");
                writer.println(max);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Input error. File doesn't exist");
        } finally {
            if (fileScanner != null) {
                fileScanner.close();
            }
        }
    }
}