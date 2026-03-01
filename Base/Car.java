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

    

    // Java Extension Pack 可以自動生成 getter 方法

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