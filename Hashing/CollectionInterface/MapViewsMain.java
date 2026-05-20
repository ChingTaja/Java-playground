package Hashing.CollectionInterface;

import java.util.*;

public class MapViewsMain {

    public static void main(String[] args) {

        // 1. 初始化 Map 並放入資料
        Map<String, Contact> contacts = new HashMap<>();
        // 陸續從電話與信箱資料庫中讀取資料放入 Map
        // 由於 Map.put() 具備「後蓋前」特性，若名字重複，後者（Email 版本的 Contact）會取代前者
        ContactData.getData("phone").forEach(c -> contacts.put(c.getName(), c));
        ContactData.getData("email").forEach(c -> contacts.put(c.getName(), c));

        // =================================================================================
        // 核心觀念 A：取得【底層連動的視圖】 (View Collection)
        // =================================================================================
        // keySet() 回傳的是一個「視圖（View）」，它沒有獨立的記憶體儲存空間，完全連動 contacts 地圖。
        // 因為底層是 HashMap，所以此時印出來的 key 順序是隨機、無序的。
        Set<String> keysView = contacts.keySet();
        System.out.println(keysView);

        // =================================================================================
        // 核心觀念 B：建立【獨立的複本】 (Copy)
        // =================================================================================
        // 透過 new TreeSet<>(...) 構造函數，Java 會在 Heap 配置一塊「全新獨立」的記憶體空間，
        // 並把當時 keySet 的資料「複製（Copy）」一份進去。
        // 因為是 TreeSet，所以這份獨立的複本會自動依照字母 A-Z 幫你排好序。
        Set<String> copyOfKeys = new TreeSet<>(contacts.keySet());
        System.out.println(copyOfKeys);

        // 檢查 Map 中是否包含特定 Key（這比以前還要 new 一個 Contact 物件來 equals 方便太多了）
        if (contacts.containsKey("Linus Van Pelt")) {
            System.out.println("Linus and I go way back, so of course I have info");
        }

        // =================================================================================
        // 實驗一：操作【視圖（keysView）】，會同步影響原始 Map 嗎？ 會！
        // =================================================================================
        // 關鍵雷區：我們明明是把 "Daffy Duck" 從「Key 的視圖」中移除...
        keysView.remove("Daffy Duck");
        System.out.println(keysView); // 視圖中確實不見了

        // 證明：再次印出原始 contacts view，你會發現 Daffy Duck 聯絡人「整筆（Key 與 Value）被斬草除根」
        // 原因：因為 keysView 只是 contacts 的 view，底層資料源直接連動受傷。
        contacts.forEach((k, v) -> System.out.println(v));

        // =================================================================================
        // 實驗二：操作【獨立複本（copyOfKeys）】，會同步影響原始 Map 嗎？ 不會
        // =================================================================================
        // 我們從獨立的 TreeSet 複本中移除 "Linus Van Pelt"
        copyOfKeys.remove("Linus Van Pelt");
        System.out.println(copyOfKeys); // 複本中確實不見了

        // 證明：再次印出原始 contacts 地圖，Linus 依然完好無缺地躺在 Map 裡面
        // 原因：因為 copyOfKeys 是獨立配置的記憶體，它的生死與原始 Map 完全脫鉤
        // 警告：如果原本的目的是想透過操作 Set 來管理地圖資料，千萬不要誤寫成 new 構造函數
        contacts.forEach((k, v) -> System.out.println(v));

        // =================================================================================
        // 實驗三：利用視圖的批量操作（如 retainAll）來進行 Map 大清洗
        // =================================================================================
        // retainAll 的意思是「只保留符合的元素，其餘全部刪除」（與 removeAll 相反）。
        // 這裡我們對視圖指定只保留這四個人。
        keysView.retainAll(List.of("Linus Van Pelt", "Charlie Brown",
                "Robin Hood", "Mickey Mouse"));

        // 證明：操作擴散到了原始數據源！現在 keysView 只剩 4 個人，
        // 而底層的 contacts 地圖也瞬間被淨化，只剩下這 4 位聯絡人，其餘不相干的聯絡人全部自動被 purge（清除）了
        System.out.println(keysView);
        contacts.forEach((k, v) -> System.out.println(v));

        keysView.clear();
        System.out.println(contacts);

        // 1. 重新用資料初始化 Map
        // 💡 影片提到：前面雖然用 keysView.clear() 清空了地圖，但只要重新 put 資料，
        // keysView 不需要重新讀取，就會「自動同步刷新」看到新資料，因為它是即時連動的窗戶！
        ContactData.getData("email").forEach(c -> contacts.put(c.getName(), c));
        ContactData.getData("phone").forEach(c -> contacts.put(c.getName(), c));
        System.out.println(keysView);

        // =================================================================================
        // 核心觀念 A：使用 values() 取得「值的集合視圖」 (Collection View)
        // =================================================================================
        // values() 回傳的是 Collection<Contact> 而不是 Set，因為 Map 中的「值」是允許重複的。
        var values = contacts.values();
        values.forEach(System.out::println);

        // 實驗一：對 values 視圖使用 retainAll() 進行過濾
        // 邏輯：只保留「同時存在於 Email 資料集」中的 Contact 物件。
        // 副作用：因為是連動視圖，不符合的值被刪除後，對應的 Key 也會自動在 Map 與 keysView 中消失！
        values.retainAll(ContactData.getData("email"));
        System.out.println(keysView); // 觀察發現：Key 的視圖也同步變少了
        contacts.forEach((k, v) -> System.out.println(v));

        System.out.println("------------------");
        // View 強大之處：它可以直接丟進任何 Collection 實作類別的構造函數（例如 ArrayList）
        // 這裡我們將 values 視圖傳入 ArrayList 以建立一份「獨立複本」，並對其進行自訂排序
        List<Contact> list = new ArrayList<>(values);
        // 使用自訂的 getNameLastFirst（姓, 名）格式進行 A-Z 排序
        list.sort(Comparator.comparing(Contact::getNameLastFirst));
        list.forEach(c -> System.out.println(c.getNameLastFirst() + ": " + c));

        System.out.println("------------------");
        // =================================================================================
        // 實驗二：故意製造「重複的值（Duplicate Values）」但「不同的 Key」
        // =================================================================================
        Contact first = list.get(0); // 取得排序後的第一個聯絡人（例如：Daffy Duck）

        // 故意用不同的 Key 格式（"Duck, Daffy"），但放入同一個 Contact 物件（Daffy Duck）
        // 此時 Map 內會有兩個不同的 Key 指向同一個 Contact 實例！
        contacts.put(first.getNameLastFirst(), first);

        values.forEach(System.out::println); // 會印出兩個 Daffy Duck (因為 Value 允許重複)
        keysView.forEach(System.out::println); // 印出的 Key 則是唯一的 ("Daffy Duck" 與 "Duck, Daffy")

        // =================================================================================
        // 實驗三：利用 HashSet 檢查 Map 中是否含有重複的 Value
        // =================================================================================
        // 將 values 丟進 HashSet 構造函數
        // 由於 Set 會自動過濾掉「重複的物件（equals 相同者）」
        HashSet<Contact> set = new HashSet<>(values);
        set.forEach(System.out::println); // 重複的 Daffy Duck 物件在 Set 裡被合併，只剩一個

        // 經典面試題演練：如果「不重複的 Value 數量」小於「Key 的總數量」
        // 就代表 Map 中絕對有「多個不同的 Key 共享同一個 Value」的情況！
        if (set.size() < contacts.keySet().size()) {
            System.out.println("Duplicate Values are in my Map"); // 成功觸發此條件
        }

        // =================================================================================
        // 核心觀念 B：使用 entrySet() 探索 Map 底層真實的「Node 節點 view」
        // =================================================================================
        // entrySet() 回傳的是一個裝滿 Map.Entry<K, V> 的 Set 集合
        // 在 HashMap 的底層源碼中，這個 Entry 介面的具體實作類別就是一個 static 巢狀類別叫做「Node」
        var nodeSet = contacts.entrySet();
        for (var node : nodeSet) {
            // 💡 探查原始碼類別名稱：會印出 java.util.HashMap$EntrySet (這是 HashMap 的內部類別)
            System.out.println(nodeSet.getClass().getName());

            // 檢查：地圖的【Key】是否不等於【Value 物件內部紀錄的 Name】？
            // 正常來說應該要相等，但因為我們剛才故意用 "Duck, Daffy" 當作 Key，這裡就會抓到不一致！
            if (!node.getKey().equals(node.getValue().getName())) {
                // 💡 探查底層節點類別：會印出 java.util.HashMap$Node (證實底層是用 Node 實作 Entry 介面)
                System.out.println(node.getClass().getName());
                System.out.println("Key doesn't match name: " + node.getKey() + ": " +
                        node.getValue());
            }
        }

    }
}
