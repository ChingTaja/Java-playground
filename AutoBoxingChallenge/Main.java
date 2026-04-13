package AutoBoxingChallenge;

import java.util.ArrayList;

// 只需要一個「純粹用來裝資料的容器」時
// Record 傾向於「不可變」, 用來消除冗餘程式碼
// 我們依然可以透過它的 ArrayList 欄位來動態增加交易紀錄。這符合「客戶名字不變，但交易一直增加」的邏輯
record Customer(String name, ArrayList<Double> transactions) {
    public Customer(String name, double initialDespoit) {
        this(name.toUpperCase(), new ArrayList<Double>(500));
        transactions.add((initialDespoit));
    }
}

public class Main {
    public static void main(String[] args) {
        Customer bob = new Customer("bob S", 10000.0);
        System.out.println(bob);

        Bank bank = new Bank("chese");
        bank.addNewCustoer("Jane A", 500);

        bank.printStatement("Jane a");
        
    }
}

class Bank {
    private String name;
    private ArrayList<Customer> customers = new ArrayList<>(5000);

    public Bank(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bank [name=" + name + ", customers=" + customers + "]";
    }

    private Customer getCustomer(String customerName) {
        for (var customer : customers) {
            if (customer.name().equalsIgnoreCase(customerName)) {
                return customer;
            }
        }
        System.out.println(customerName);
        return null;
    }

    public void addNewCustoer(String customerName, double initialDespoit) {
        if (getCustomer(customerName) == null) {
            Customer customer = new Customer(customerName, initialDespoit);
            customers.add(customer);
            System.out.println(customer);
        }
    }

    public void addTransaction(String name, double transactionAmount) {
        Customer customer = getCustomer(name);
        if (customer != null) {
            // getTransactions() -> transactions
            // 雖然 Customer 是個 Record, 它的欄位是 final（不能換掉整個清單）, 但它交給你的那個 ArrayList 物件本身是可變的
            customer.transactions().add(transactionAmount);
        }
    }

    public void printStatement(String customerName) {
        Customer customer = getCustomer(customerName);
        if (customer == null) {
            return;
        }
        for (double d : customer.transactions()) {
            System.out.printf("$%10.2f (%s)%n", d , d < 0 ? "debit" : "credit");
        }
    }
}
