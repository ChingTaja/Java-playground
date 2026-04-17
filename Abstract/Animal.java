package Abstract;

//  抽象類別繼承抽象類別

/* 
 不必立即實作
 三種選擇：

- 完全不實作父類別的抽象方法
- 只實作部分的抽象方法
- 實作全部的抽象方法

責任延後：
實作抽象方法的責任會一直往後推
直到出現第一個「具體類別」（如 Horse）為止
此時，該具體類別必須實作所有祖先類別中尚未完成的抽象方法
*/

abstract class Mammal extends Animal {

    public Mammal(String type, String size, double weight) {
        super(type, size, weight);
    }

    @Override
    public void move(String speed) {

        System.out.print(getExplicitType() + " ");
        System.out.println( speed.equals("slow") ? "walks" : "runs");
    }

    public abstract void shedHair();
}

public abstract class Animal {

    // protected 讓子類別可以直接用父類變數 , 不用透過 getter/setter
    protected String type;
    private String size;
    private double weight;

    public Animal(String type, String size, double weight) {
        this.type = type;
        this.size = size;
        this.weight = weight;
    }

    public abstract void move(String speed);
    public abstract void makeNoise();

    // 抽象類別不只能有抽象方法，也可以擁有具體方法
    // 子類別（如 Dog 或 Fish）可以直接繼承並使用這些具體方法，不需要重新撰寫相同的邏輯

    // final: 禁止覆寫（Override） 希望子類別「強制使用」父類別定義的方法邏輯
    public final String getExplicitType() {
        return getClass().getSimpleName() + " (" + type + ")";
    }
}
