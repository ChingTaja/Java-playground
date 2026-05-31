package NoArgsConstructor;

import NoArgsConstructor.Child;
import NoArgsConstructor.Parent;

public class Main {

    public static void main(String[] args) {

        // 觀察點：當第一次使用到 Parent / Child 類別時，會依序觸發：
        // 1. 父類別的靜態初始化區塊（一生一次）
        // 2. 父類別實例區塊 -> 父類別建構子
        // 3. 子類別實例區塊（此時 birthOrder 已先指派） -> 子類別建構子

        Parent parent = new Parent(
                "Jane Doe",
                "01/01/1950",
                4);

        Child child = new Child();

        System.out.println("Parent: " + parent);
        System.out.println("Child: " + child);
    }
}
