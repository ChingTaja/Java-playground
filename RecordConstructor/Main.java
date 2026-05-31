// Record 它的核心目標是「用最少程式碼，包裝一份絕對不能被修改的資料」。因此，它的欄位在底層全都是 `final`（一旦指派就不能變更）
package RecordConstructor;

public class Main {

    public static void main(String[] args) {

        // =====================================================
        // Parent / Child 初始化順序示範
        // =====================================================

        Parent parent = new Parent(
                "Jane Doe",
                "01/01/1950",
                4);

        Child child = new Child();

        System.out.println("Parent: " + parent);
        System.out.println("Child: " + child);

        // =====================================================
        // Record 示範
        // =====================================================

        Person joe = new Person(
                "Joe",
                "01-01-1950");

        System.out.println(joe);

        // Copy Constructor
        Person joeCopy = new Person(joe);

        System.out.println(joeCopy);

        // =====================================================
        // Enum 示範
        // =====================================================

        Generation g = Generation.BABY_BOOMER;
    }
}
