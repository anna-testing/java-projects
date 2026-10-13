package org.example;

import java.util.Scanner;

public class ConverterSeconds {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int seconds = inputSeconds(scanner);
            String result = calculateTime(seconds);
            output(result);
        }
    }

    public static int inputSeconds(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter seconds: ");
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    throw new NumberFormatException();
                }
                int seconds = Integer.parseInt(line);
                return seconds;
            } catch (NumberFormatException e) {
                System.out.println("Could not parse a number. Please, try again");
            }
        }
    }

    public static String calculateTime(int totalSeconds) {
        if (totalSeconds < 0) {
            return "Incorrect time";
        }
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static void output(String time) {
        System.out.println(time);
    }
}
