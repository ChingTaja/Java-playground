import Base.Customer;

public class CustomerMain {
    public static void main(String[] args) {
        Customer customer = new Customer("Tim", 1222, "tim@xxxxx");
        System.out.println(customer.getName());
        System.out.println(customer.getCreditLimit());
        System.out.println(customer.getEmailAddress());

        Customer secondCustomer = new Customer();
        System.out.println(secondCustomer.getName());
        System.out.println(secondCustomer.getCreditLimit());
        System.out.println(secondCustomer.getEmailAddress());
    }
}
