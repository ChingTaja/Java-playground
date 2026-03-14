package Inheritance;
// Implicit super constructor Animal() is undefined for default constructor -> 要先有父親，才有兒子
/* 
子類的構 Constructor 第一行，必須調用父類的構造函數

如果沒寫 Java 會自動幫你補上一行 super()
（這就是調用父類的無參數構造函數）

問題點： 因為你在 Animal 類別裡寫了一個「有參數」的構造函數
Java 就不會幫你自動生成「無參數」的默認構造函數
*/
public class Dog extends Animal {
    // 如果什麼都沒寫 , Java 自動幫你補了 super(); 
    // 但 Animal 類別裡現在沒有 無參數的Animal()，所以報錯！

    private String earShape;
    private String tailShape;
    // 子類有義務幫父類完成初始化
    public Dog() {
        super("Mut", "Big", 40);
    }

    public Dog(String type, double weight) {
        this(type, weight, "perky", "curled");
    }

    public Dog(String type, double weight, String earShape, String tailShape) {
        super(type, weight < 15 ? "small" : (weight < 35 ? "medium" : "large"), weight);
        this.earShape = earShape;
        this.tailShape = tailShape;
    }
   
    @Override
    public String toString() {
        return super.toString() +
                ", earShape=" + earShape +
                ", tailShape=" + tailShape;
    }
        
    // 1. 完全替換 (Complete Override)
    // 父類的行為被完全捨棄，改用子類定義的行為
    @Override
    public void makeNoise() {
        // 子 class 無法讀取 母 class 的 private 屬性
        // 1. 不同 package 的子 class 可以讀取 母 class 的 protected 屬性(conditional encapsulation)
        // 2. protected 同一個 package 的 class都可以存取它

        if (type == "Wolf") {
            System.out.print("Ow Wooo!");
        }
        bark();
        System.out.println();
    }

    // 2. 直接繼承 (Redundant Override / Default)
    
    //雖然在最終效果上是多餘的，但在以下幾種特殊情況下，可能會這樣做：
    // 1. 為了以後擴充： 先把架構搭好，打算以後再回來加代碼
    // 2. 修改權限： 雖然行為一樣，但想把方法的權限從「保護（protected）」改成「公開（public）」
    // 加上註解： 雖然行為一樣，但想在子類的方法上寫註釋說明
    @Override
    public void move(String speed) {
        super.move(speed);
        if (speed == "slow") {
            walk();
            wagTail();
        } else {
            run();
            bark();
        }
        System.out.println();
    }
    
    // 3. 功能擴充 (Extension)
    // 既保留父類的基礎，又增加子類的特色()
    // 先呼叫 super.method() 接著再寫子類專屬的程式碼
    /* 
    @Override
    public void move(String speed) {
        super.move(speed);
        System.out.println("hi");
    }
    */
    
    private void bark() {
        System.out.println("Woof!");
    }

    private void run() {
        System.out.println("Dog Running ");
    }
    
    private void walk() {
        System.out.println("Dog wakling ");
    }
    
    private void wagTail() {
        System.out.println("Tail Wagging ");
    }
}
