package Base;

public class BankAccountMain {
    public static void main(String[] args) {
        BankAccount bobsAccount = new BankAccount();
        bobsAccount.withdrawFunds(100);
        bobsAccount.depositFunds(250);
        bobsAccount.withdrawFunds(50);
        bobsAccount.withdrawFunds(200);
    }
}
