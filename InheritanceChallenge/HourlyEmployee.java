package InheritanceChallenge;

public class HourlyEmployee extends Employee{

    private double houltPayRate;

    public HourlyEmployee(String name, String birthDate, String hireDate, double houltPayRate) {
        super(name, birthDate, hireDate);
        this.houltPayRate = houltPayRate;
    }

    @Override
    public double collectPay() {
        return 40 * houltPayRate;
    }
    
    public double getDoublePay() {
        return 2 * collectPay();
    }
}
