// 如何 初始化一個 ArrayList
package ArrayList;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

        System.out.println("Third item = " + groceries.get(2));

        if (groceries.contains("mustard")) {
            System.out.println("List contains mustard");
        }

        groceries.add("yogard");
        System.out.println("first" + groceries.indexOf("yoguart"));
        System.out.println("first" + groceries.lastIndexOf("yoguart"));

        System.out.println(groceries);
        groceries.remove(1);
        groceries.remove("yogurt");
        groceries.removeAll(List.of("apples"));

        // 保留同時存在於 groceries 和 （....） 裡面的東西
        // 它會直接修改「原清單」
        groceries.retainAll(List.of("apples", "like", "mustard"));

        groceries.clear();
        System.out.println("isEmpty = " + groceries.isEmpty());

        groceries.addAll(List.of("apple", "milk", "mustard"));
        groceries.addAll(Arrays.asList("eggs", "pickles"));

        // Comparator 是一個介面
        groceries.sort(Comparator.naturalOrder());
        groceries.sort(Comparator.reverseOrder());

        // 這行程式碼是將ArrayList 轉回「原生陣列」(Array) 最常用的寫法
        // 如果你只寫 groceries.toArray()，Java 會回傳一個 Object[]因為 Object 是所有類別的祖先，你沒辦法直接把它當成 String[] 來用
        // 告訴 Java：「請幫我準備一個 String 型別 且 長度剛好 的陣列來裝這些資料。」
        var groceryArray = groceries.toArray(new String[groceries.size()]);

        System.out.println(Arrays.toString(groceryArray));
        
        // 告訴 Java 「我想要 String 型別」。Java 會自動偵測到長度不夠，然後在底層幫你建立一個長度正確的陣列。這種寫法更簡潔，效能有時候甚至更好。
        groceries.toArray(new String[0]);
    }
}
