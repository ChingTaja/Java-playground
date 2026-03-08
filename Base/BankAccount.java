package Base;

public class BankAccount {
    private String accountNumber;
    private double balance;
    private String customerName;
    private String email;
    private String phoneNumber;

    public BankAccount() {
        this("1234", 123, " tajaja", "taja@gmail.com", "09xxxxx"); // must be first line !!
    }

    // constructor 必須和 class 名稱完全一樣
    // 只要名字不一樣，Java 就會把它當成普通 method
    public BankAccount(String accountNumber, double balance, String customerName, String customerEmail,
            String custmoerPhone) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customerName = customerName;
        email = customerEmail;
        phoneNumber = custmoerPhone;
    }
    
    public BankAccount(String customerName, String customerEmail, String customerPhone) {
        this("11111", 1233, customerName, customerEmail, customerPhone);
        this.customerName = customerName;
        email = customerEmail;
        phoneNumber = customerPhone;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public double getBalance() {
        return balance;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void depositFunds(double depositAmount) {
        // 這裡的 balance 不用加上 this
        // 因為這個方法裡沒有其他叫 balance 的變數, 也沒有傳入參數叫 balance
        // 所以 Java 編譯器知道你指的是類別的欄位, 自然不用 this 也可以
        balance += depositAmount;
        System.out.println("Deposit of $" + depositAmount + " made . New balance is $" + this.balance);
    }

    public void withdrawFunds(double withdrawalAmount) {
        if (balance - withdrawalAmount < 0) {
    System.out.println("Insufficient Funds! You only have $" + balance + " in your account.");
} else {
    balance -= withdrawalAmount;
    System.err.println("Withdrawal of $" + withdrawalAmount + " processed, Remaining balance = $" + balance);
}
    }
}
