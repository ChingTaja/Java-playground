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
    }

    public static double calculateInterest(double amount, double interestRest) {
        return (amount * (interestRest / 100));
    }
}
