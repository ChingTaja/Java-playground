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
}
