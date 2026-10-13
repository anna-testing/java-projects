package org.example;

import java.util.Scanner;

public class TrianglePerimeter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double[] coords = new double[6];
        int index = 0;

        while (index < 6) {
            try {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    throw new NumberFormatException();
                }
                double num = Double.parseDouble(line);
                coords[index] = num;
                index++;
            } catch (NumberFormatException e) {
                System.out.println("Could not parse a number. Please, try again");
            }
        }

        double x1 = coords[0], y1 = coords[1];
        double x2 = coords[2], y2 = coords[3];
        double x3 = coords[4], y3 = coords[5];

        double area = Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
        final double EPSILON = 1e-9;

        if (area < EPSILON) {
            System.out.println("It's not a triangle");
        } else {
            double ab = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
            double bc = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
            double ca = Math.sqrt((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3));
            double perimeter = ab + bc + ca;
            System.out.printf("Perimeter: %.3f%n", perimeter);
        }

        scanner.close();
    }
}