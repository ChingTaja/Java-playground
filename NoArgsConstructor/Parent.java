//  一旦手動寫了任何一個「自訂建構子」，Java 官方就絕對不會再主動幫你生成隱式的無參建構子
package NoArgsConstructor;

public class Parent {

    // 🔬 1. 靜態初始化區塊（Static Initializer Block）
    // 類別第一次被載入 JVM 時執行（只執行一次）
    static {
        System.out.println("Parent static initializer: class being constructed");
    }

    // =====================================================
    // final 欄位
    // =====================================================

    private final String name;
    private final String dob;

    // 允許子類別存取
    protected final int siblings;

    // =====================================================
    // 2. 實例初始化區塊（Instance Initializer Block）
    // =====================================================

    {
        // ⚠️ 每次 new 物件都會先執行（早於 constructor）

        // ❗ final 欄位不能在這裡或 constructor 重複賦值

        System.out.println("In Parent Initializer");
    }

    // =====================================================
    // ❌ 無參數建構子（已註解）
    // =====================================================

    /*
     * public Parent() {
     * System.out.println("In Parent's No Args Constructor");
     * }
     */

    // ⚠️ 重要觀念：
    // 一旦你寫了任何自訂 constructor
    // JVM 就不會再自動生成 default no-args constructor

    // =====================================================
    // 3. 有參數建構子
    // =====================================================

    public Parent(String name, String dob, int siblings) {

        this.name = name;
        this.dob = dob;
        this.siblings = siblings;

        System.out.println("In Parent Constructor");
    }

    // =====================================================
    // Getter
    // =====================================================

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    // =====================================================
    // toString
    // =====================================================

    @Override
    public String toString() {
        return "name='" + name + '\'' + ", dob='" + dob + '\'';
    }
}