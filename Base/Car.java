package Base;

public class Car {
    private String make;
    private String model;
    private String color;
    private int doors;
    private boolean convertible;

    public String getMake() {        
        return make;
    }

    // Java Extension Pack 可以自動生成 getter & setter 方法

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public int getDoors() {
        return doors;
    }

    public boolean isConvertible() {
        return convertible;
    }

    public void setMake(String make) {
        // make = make;
        if (make == null)
            make = "Unknown";
        String lowercaseMake = make.toLowerCase();
        switch (lowercaseMake) {
            case "holden", "porsche", "teska" -> this.make = make;
            default -> {
                this.make = "Unsupported";
            }
        }
        /*
         * 我有兩個不同的變數都叫做 make：
         * 
         * 1. 欄位（field）：`private String make`
         * 2. 方法參數（parameter）：`String make`
         * 
         * 在程式裡，實際上不是把欄位 make 設為參數 make，
         * 而是把 參數 make 指派給自己。
         * 
         * 要做的是：
         * 把 private String make 這個欄位，更新成傳入方法的參數值
         * 
         * ==> 使用 this
         */
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public void setConvertible(boolean convertible) {
        this.convertible = convertible;
    }
    

    // 和欄位不同的是，方法通常會設為 public
    // 因為我們希望提供一個方式，讓使用者可以與物件進行互動
    public void describeCar() {
        System.out
                .println(doors + "-Door" + color + " " + make + " " + model + " " + (convertible ? "Convertible" : ""));
    }

    /*
     * 我想要的是：
     * 每次建立 Car 物件時
     * 都能設定不同的 make、model 和 color
     * 
     * 問題是:
     * 因為我把這些欄位宣告成 private，
     * 我沒辦法在外部使用dot notation）來直接設定值
     * * --> 借用到 getter & setter 的概念
     */
}