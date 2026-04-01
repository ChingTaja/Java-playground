package ArrayList;
import java.util.ArrayList;
import java.util.Arrays;

record GroceryItem(String name, String type, int count) {
    // cusotm constuctor
    public GroceryItem(String name) {
        this(name, "DAIRY", 1);
    }

    @Override
    public String toString() {
        return String.format("%d %s in %s", count, name.toUpperCase(), type);
    }
}
public class Main {
    public static void main(String[] args) {
        GroceryItem[] groceryArray = new GroceryItem[3];
        groceryArray[0] = new GroceryItem("milk");
        groceryArray[1] = new GroceryItem("apple", "PRODUCE", 6);
        // groceryArray[2] = "5 oranges"; -> Error
        groceryArray[2] = new GroceryItem("oranges", "PRODUCE", 5);
        System.out.println(Arrays.toString(groceryArray));

        //從結構僵硬的陣列 (Array) 轉向更靈活、且具備型別安全性的 ArrayList 與 泛型 (Generics)

        // 風險： 你可以把 GroceryItem 放進去，下一秒又把 String（字串）放進去
        ArrayList objectList = new ArrayList();
        objectList.add(new GroceryItem("Butter"));
        objectList.add("Yoguart");

        // Generics
        // 自 Java 7 開始，只要左邊定義過型別，右邊就不用重複寫<型別>，編譯器會自動推斷
        ArrayList<GroceryItem> groceryList = new ArrayList<>();
        groceryList.add(new GroceryItem("Butter"));
        groceryList.add(new GroceryItem("milk"));
        groceryList.add(new GroceryItem("orange", "PRODUCE", 5));
        groceryList.set(0, new GroceryItem("apples", "PRODUCE", 6));
        groceryList.remove(1);
        // groceryList.add("Yoguart"); -> Error

        //  不像原生陣列需要外援才能印出來
        // 因為 ArrayList 類別內部已經寫好了 toString() 的邏輯
        System.out.println(groceryList);
    }
}
