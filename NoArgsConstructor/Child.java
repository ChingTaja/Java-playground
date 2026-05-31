package NoArgsConstructor;

import NoArgsConstructor.Parent;

import java.util.Random;


public class Child extends Parent {

    // =====================================================
    // final 欄位初始化（依賴父類別已完成初始化）
    // =====================================================

    private final int birthOrder = getBirthOrder();

    private final String birthOrderString;

    // =====================================================
    // Instance Initializer Block（實例初始化區塊）
    // =====================================================

    {
        // ⚠️ final 欄位必須在「所有分支」都被賦值

        if (siblings == 0) {
            birthOrderString = "Only";
        } else if (birthOrder == 1) {
            birthOrderString = "First";
        } else if (birthOrder == (siblings + 1)) {
            birthOrderString = "Last";
        } else {
            birthOrderString = "Middle";
        }

        System.out.println(
                "Child: Initializer, birthOrder = "
                        + birthOrder
                        + ", birthOrderString = "
                        + birthOrderString);
    }

    // =====================================================
    // Constructor
    // =====================================================

    public Child() {

        // ❗ 必須明確呼叫 super()
        // 因為 Parent 沒有無參數 constructor
        super("Jane Doe", "02/02/1920", 5);

        System.out.println("Child: Constructor");
    }

    // =====================================================
    // Method used in field initialization
    // =====================================================

    private final int getBirthOrder() {

        if (siblings == 0)
            return 1;

        return new Random().nextInt(1, siblings + 2);
    }

    // =====================================================
    // Override
    // =====================================================

    @Override
    public String toString() {
        return super.toString() + ", " + birthOrderString + " child";
    }
}