package InheritanceChallenge;

public class Main {

    public static void main(String[] args) {
        Employee tim = new Employee("Tim", "07/09/1922", "01/02/2020");

        System.out.println("Age" + tim.getAge());

        SalariedEmployee joe = new SalariedEmployee("Joe", "11/1/1990", "02/03/2017", 70000);

        System.out.println("Joe;s paycheck = $" + joe.collectPay());

        HourlyEmployee mary = new HourlyEmployee("Mary", "05/05/1970", "02/02/2020", 14);
        System.out.println(mary.collectPay());
        System.out.println(mary.getDoublePay());
    }
}
