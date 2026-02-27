package Base;

import java.util.Scanner;

public class InputChallenge {
    public static void main(String[] args) {
        checkFiveValidNumber();
    }

    public static void checkFiveValidNumber() {
        Scanner scanner = new Scanner(System.in);
        int count = 1;
        double sum = 0;
        while (count <= 5) {
            System.out.println("Enter number #" + count + ":");
            String nextNumber = scanner.nextLine();
            try {

                double number = Double.parseDouble(nextNumber);
                System.out.println(nextNumber);
                count++;
                sum += number;
            } catch (NumberFormatException invalidValue) {
                System.out.println("Invalid number");
            }
        }
        System.out.println("The sum of the 5 numbers =" + sum);
    }

    public static void identifyMinAndMMax() {
        Scanner scanner = new Scanner(System.in);

        double max = 0;
        double min = 0;
        int loopCount = 0;
        while (true) {
            System.out.println(("Enter a number"));
            String nextEntry = scanner.nextLine();
            try {
                double validNum = Double.parseDouble(nextEntry);
                if (loopCount == 0 || validNum < min) {
                    min = validNum;
                }
                if (loopCount == 0 || validNum > max) {
                    max = validNum;
                }
                loopCount++;
            } catch (NumberFormatException nfe) {
                break;
            }
        }
        if (loopCount > 0) {
            System.out.println(("min =" + min + ",max = " + max));
        } else {
            System.out.println(("No valid data ented"));
        }
    }
}
