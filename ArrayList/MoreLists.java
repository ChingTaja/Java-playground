// 如何 初始化一個 ArrayList
package ArrayList;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MoreLists {
    public static void main(String[] args) {

        String[] items = { "apple", "bananas", "milk", "eggs" };
    
        // Factory Method
        // 它會把陣列裡的資料「複製」到一個新的、唯讀的清單物件
        // !! 不可變 你不能對它執行 .add() 或 .remove()
        List<String> list = List.of(items);
        System.out.println(list);

        System.out.println(list.getClass().getName());
        ArrayList<String> groceries = new ArrayList<>(list);
        groceries.add("yoguart");
        System.out.println(groceries);

        // 用 List.of 產生資料 -> 丟進 ArrayList 的建構子 (Constructor) 將「不可變」轉為「可變」
        ArrayList<String> nextList = new ArrayList<>(List.of("pickles", "mustard", "cheeese"));
        System.out.println(nextList);

        groceries.addAll(nextList);
        System.out.println(groceries);
    }
}
