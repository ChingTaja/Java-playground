package Base;

public class BankAccountMain {
    public static void main(String[] args) {
        BankAccount bobsAccount = new BankAccount("124",100,"Taja", "taja@email.com", "09123444");
        // bobsAccount.setAccountNumber("124");
        // bobsAccount.setBalance(1000);
        // bobsAccount.setCustomerName("Taja");
        // bobsAccount.setEmail("taja@email.com");
        bobsAccount.withdrawFunds(100);
        bobsAccount.depositFunds(250);
        bobsAccount.withdrawFunds(50);
        bobsAccount.withdrawFunds(200);
    }
}
