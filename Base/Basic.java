package Base;

public class Basic {
    public static void main(String[] args) {
        Car car = new Car(); // 告一個型別為 Car 的變數

        // car.make = "Porsche"; // Error!
        System.out.println("make =" + car.getColor()); 

        car.describeCar(); // 0 door null, null, null
    }
}
