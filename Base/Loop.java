package Base;

public class Loop {
    public static void main(String[] args) {
        for (int counter = 1; counter <= 5; counter++) {
            System.out.println(counter);
        }

        for (double rate = 2.0; rate <= 5; rate++) {
            double interestAmount = calculateInterest(1000.0, rate);
            System.out.println(rate + "/" + interestAmount);
        }

        for (int i = 1; i <= 10; i++) {

            if (i == 5) {
                break; // 當 i 等於 5 時，立刻跳出迴圈
            }

            System.out.println(i);
        }

        int count = 0;
        for (int i = 10; count < 3 && i < 50; i++) {
            if (isPrime(i)) {
                System.out.println(("number" + i));
                count++;

                // if(count == 3) {
                // System.out.println("break loop");
                // break;
                // }
            }
        }

        // while
        int j = 1;
        while (j <= 5) {
            System.out.println(j);
            j++;
        }

        // continue(跳過這個 loop 進入下一個 loop)
        int number = 0;
        while ( number < 50) {
            number += 5;
            if (number % 25 == 0) {
                continue;}
            System.out.print(number + "_");
        }

        // do while (至少執行一次)
        int k = 1;
        boolean isReady = false;
        do {
            if (k > 5) {
                break;
            }
            System.out.println(k);
        } while (isReady);
    }

    public static double calculateInterest(double amount, double interestRest) {
        return (amount * (interestRest / 100));
    }

    public static boolean isPrime(int wholeNumber) {
        if (wholeNumber <= 2) {
            return (wholeNumber == 2);
        }
        for (int divisor = 2; divisor < wholeNumber; divisor++) {
            if (wholeNumber % divisor == 0) {
                return false;
            }
        }
        return true;
    }
}
