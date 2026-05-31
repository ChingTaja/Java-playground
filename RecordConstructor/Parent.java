package RecordConstructor;

public class Parent {

    // =====================================================
    // Static Initializer Block
    // 類別第一次被載入時執行（只會執行一次）
    // =====================================================

    static {
        System.out.println("Parent static initializer: class being constructed");
    }

    // =====================================================
    // Instance Fields
    // =====================================================

    private final String name;
    private final String dob;

    // 允許子類別存取
    protected final int siblings;

    // =====================================================
    // Instance Initializer Block
    // 每次建立物件時執行，且早於 Constructor
    // =====================================================

    {

        // name = "John Doe";
        // dob = "01/01/1900";

        System.out.println("In Parent Initializer");
    }

    // =====================================================
    // No-Args Constructor（已註解）
    // =====================================================

    /*
     * public Parent() {
     * System.out.println("In Parent's No Args Constructor");
     * }
     */

    // =====================================================
    // Constructor
    // =====================================================

    public Parent(String name, String dob, int siblings) {

        this.name = name;
        this.dob = dob;
        this.siblings = siblings;

        System.out.println("In Parent Constructor");
    }

    // =====================================================
    // Getters
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
        return "name='" + name + '\'' +
                ", dob='" + dob + '\'';
    }
}
