package Base;

public class Basic {
    public static void main(String[] args) {
        Car car = new Car(); // 告一個型別為 Car 的變數

        // car.make = "Porsche"; // Error!
        car.setMake("Porsche");
        car.setModel("Carrera");
        car.setDoors(2);
        car.setConvertible(true);
        car.setColor("black");
        System.out.println("make =" + car.getColor());

        car.describeCar(); // 0 door null, null, null
    }
    
    /*
     * 這裡有一個重要區別：未初始化變數 與 指向 null 的變數。
     * 
     * 未初始化變數： 會在 編譯時（compile-time） 發生錯誤
     * 指向 null 的變數：編譯不會報錯，但在執行時會 丟出例外
     */
}
