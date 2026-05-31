// `Enum`（列舉型別）的建構子機制、底層反組譯架構、實例欄位的動態指派，以及特定常數專屬的實例初始化區塊運作原理
package RecordConstructor;

import java.time.LocalDate;

/**
 * 💡 透過 javap -p 工具揭露：
 * Enum 在底層本質上是一個繼承了 java.lang.Enum 的 final 類別
 *
 * 而裡面的每一個列舉常數（Constants），其實都是：
 * static final Generation 型別的實例
 */
public enum Generation {

    // ====================
    // 1. 列舉常數宣告區 (Constants List)
    // ====================

    // 💡 特殊常數語法：
    // 為特定 enum constant 宣告「類別主體（Anonymous Class Body）」
    // 這其實是該 constant 的「子類別擴展寫法」

    // 實驗：
    // 直接在 constant 裡寫 println 會失敗
    // 因為這裡本質上是 class body，而不是 method body

    GEN_Z {

        // 💡 這是 instance initializer block（實例初始化區塊）
        // 只會在 GEN_Z 建立時執行一次
        {
            System.out.println("-- SPECIAL FOR " + this + " --");
        }
    },

    // 傳入參數給自訂建構子
    // 用來初始化 startYear / endYear 實例欄位
    MILLENNIAL(1981, 2000),
    GEN_X(1965, 1980),
    BABY_BOOMER(1946, 1964),
    SILENT_GENERATION(1927, 1945),
    GREATEST_GENERATION(1901, 1926); // 鐵律：enum 有額外程式碼必須加 ;

    // ====================
    // 2. 實例欄位宣告 (Instance Fields)
    // ====================

    // 💡 實務上強烈建議 final（不可變性）
    private final int startYear;
    private final int endYear;

    // ====================
    // 3. Enum 建構子 (Constructors)
    // ====================

    /**
     * ⚠️ enum constructor 有隱式規則：
     * 永遠是 private（不能寫 public / protected）
     */

    // ❌ 原本錯誤示範（不能巢狀 constructor）
    /*
     * Generation() {
     * this(2001, LocalDate.now().getYear());
     * }
     */

    Generation() {
        this(2001, LocalDate.now().getYear());
    }

    // ✔ 正確：透過 constructor overloading + chaining 解決

    Generation(int startYear, int endYear) {
        this.startYear = startYear;
        this.endYear = endYear;

        // 💡 這裡會在 enum instance 建構過程中執行
        // 會觸發 toString()
        System.out.println(this);
    }

    // ====================
    // 4. 方法覆寫
    // ====================

    @Override
    public String toString() {
        return this.name() + " " + startYear + " - " + endYear;
    }
}