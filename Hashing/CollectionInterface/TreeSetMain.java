package Hashing.CollectionInterface;

import java.util.Comparator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Collections;

public class TreeSetMain {
    // =================================================================================
    // 觀念重點：
    // 1. TreeSet 實作了 SortedSet 與 NavigableSet 介面，具備自動排序與強大的導覽導向功能。
    // 2. 放入 TreeSet 的物件「必須」具備比較能力。若物件本身未實作 Comparable 介面（無自然排序），
    // 則必須在初始化 TreeSet 時，於建構子中傳入一個自訂的「Comparator」。
    // =================================================================================
    public static void main(String[] args) {
        List<Contact> phones = ContactData.getData("phone");
        List<Contact> emails = ContactData.getData("email");

        // 【錯誤示範】此行會報錯：java.lang.ClassCastException: Contact cannot be cast to class
        // java.lang.Comparable
        // 原因：Contact 類別沒有實作 Comparable 介面，TreeSet 在防衛機制下不知道該如何幫它排序。

        // NavigableSet<Contact> sorted = new TreeSet<>(phones); //只寫這行會報錯

        // 正確做法 1：利用 Comparator.comparing 定義排序規則（依據 Contact 的 Name 屬性進行字母排序）
        Comparator<Contact> mySort = Comparator.comparing(Contact::getName);
        // 將自訂的比較器 mySort 傳入建構子，此時 TreeSet 就能正常運作
        NavigableSet<Contact> sorted = new TreeSet<>(mySort);
        sorted.forEach(System.out::println);

        // 觀念：String、Integer 等內建型態本身已實作 Comparable（具備自然排序），因此可以免傳 Comparator 直接使用空建構子
        NavigableSet<String> justNames = new TreeSet<>();
        phones.forEach(c -> justNames.add(c.getName()));
        System.out.println(justNames);

        // 觀念：透過傳入另一個「已設定好 Comparator 的 TreeSet」來初始化新的 TreeSet
        // 這樣新集合（fullSet）就會無縫繼承對方的排序機制，不需要重新指定 Comparator
        NavigableSet<Contact> fullSet = new TreeSet<>(sorted);
        fullSet.addAll(emails);
        fullSet.forEach(System.out::println);

        // 觀念：如何讓一般的 List 使用與 TreeSet 相同的排序規則？
        List<Contact> fullList = new ArrayList<>(phones);
        // 透過 sorted.comparator() 取得該 TreeSet 內部正在使用的比較器，並傳給 List 的 sort 方法
        fullList.addAll(emails);
        fullList.sort(sorted.comparator());// 輸出結果同樣依姓名排序，但「會包含重複資料」（因為 List 允許重複）

        fullList.forEach(System.out::println);

        // =================================================================================
        // 搜尋與導覽功能 (Min, Max, First, Last, Poll)
        // =================================================================================

        // 觀念：使用工具類別 Collections.min() / max() 時，若物件沒實作 Comparable
        // 必須手動傳入第二個參數（比較器），這裡直接跟 fullSet 借用它的 comparator()。
        Contact min = Collections.min(fullSet, fullSet.comparator());
        Contact max = Collections.max(fullSet, fullSet.comparator());

        // 觀念：SortedSet 介面提供的首尾查詢方法（實務上推薦此做法，效能更佳且更直覺，等同於上面的 min/max）
        Contact first = fullSet.first();
        Contact last = fullSet.last();

        // 複製一份新的集合來測試 poll 操作（利用建構子複製）
        NavigableSet<Contact> copiedSet = new TreeSet<>(fullSet);

        // 觀念：NavigableSet 獨有的 pollFirst() / pollLast() 方法
        // 特性：這兩個方法會「取出」首尾元素，【並且同時將該元素從原集合中永久移除】！
        Contact removedFirst = copiedSet.pollFirst(); // 回傳並移除第一個元素（例如：Charlie Brown）
        Contact removedLast = copiedSet.pollLast(); // 回傳並移除最後一個元素（例如：Robin Hood）

        copiedSet.forEach(System.out::println);

        Contact daffy = new Contact("Daffy Duck");
        Contact daisy = new Contact("Daisy Duck");
        Contact snoopy = new Contact("Snoopy");
        Contact archie = new Contact("Archie");

        for (Contact c : List.of(daffy, daisy, last, snoopy)) {
            System.out.printf("ceiling(%s)=%s%n", c.getName(), fullSet.ceiling(c));
            System.out.printf("higher(%s)=%s%n", c.getName(), fullSet.higher(c));
        }
        System.out.println("--------------------------");

        for (Contact c : List.of(daffy, daisy, first, archie)) {
            System.out.printf("floor(%s)=%s%n", c.getName(), fullSet.floor(c));
            System.out.printf("lower(%s)=%s%n", c.getName(), fullSet.lower(c));
        }

        // 建立反向排序視圖 (descendingSet)
        NavigableSet<Contact> descendingSet = fullSet.descendingSet();
        descendingSet.forEach(System.out::println);
        System.out.println("--------------------------");

        // 實驗：修改反向視圖，觀察原集合的變化
        // pollLast() 會移除並回傳「當前集合」的最後一
        // 因為 descendingSet 是反向的（Z 到 A），它的最後一個（Last）其實是字母排序最前面（A）的元素個元素
        Contact lastContact = descendingSet.pollLast();
        System.out.println("Removed " + lastContact);

        // 關鍵驗證：印出「原始集合」fullSet
        // 雖然我們剛才是對 descendingSet 操作 pollLast()，
        // 但因為 Backed Set 的特性，你會發現原本正向排序的 fullSet 裡，Charlie Brown 也被同步刪除了！
        descendingSet.forEach(System.out::println);
        System.out.println("--------------------------");
        fullSet.forEach(System.out::println);
        System.out.println("--------------------------");

        // ====================
        // 2. 截取頭部區段 (headSet)
        // ====================
        Contact marion = new Contact("Maid Marion");

        // headSet(元素, boolean) 負責截取從「最開頭」到「指定元素」的區段
        // 預設行為：不包含指定元素（exclusive）
        // 程式碼中傳入 true：手動強制「包含」Maid Marion 本身（變為 inclusive）
        var headSet = fullSet.headSet(marion, true);
        headSet.forEach(System.out::println);
        System.out.println("--------------------------");

        // ====================
        // 3. 截取尾部區段 (tailSet)
        // ====================
        // tailSet(元素, boolean) 負責截取從「指定元素」到「最末尾」的區段
        // 預設行為：包含指定元素（inclusive）。
        // 程式碼中傳入 false：手動強制「排除」Maid Marion 本身（變為 exclusive）
        var tailSet = fullSet.tailSet(marion, false);
        tailSet.forEach(System.out::println);
        System.out.println("--------------------------");

        // ====================
        // 4. 截取中間特定區段 (subSet)
        // ====================

        // subSet(起點, 起點是否包含, 終點, 終點是否包含)
        // 預設行為是：含起點、不含終點 [起點, 終點)
        // 程式碼設定：
        // - linus 後方傳入 false -> 排除 Linus (Linus 不會出現)
        // - marion 後方傳入 true -> 包含 Maid Marion (Maid Marion 會出現)
        // 最終只會秀出介於兩者之間，且符合此條件的聯絡
        Contact linus = new Contact("Linus Van Pelt");
        var subset = fullSet.subSet(
                linus, false, marion, true);
        subset.forEach(System.out::println);
    }
}
