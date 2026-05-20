package SortedMap;

import java.time.LocalDate;
import java.util.*;

public class Main {

    // 1. LinkedHashMap 底層維護了雙向鏈結串列，會嚴格按照「資料插入的先後順序」排列
    // 用途：適合用來模擬現實生活中的訂單流水帳（先下單的先排在前面）
    private static Map<String, Purchase> purchases = new LinkedHashMap<>();

    // 2. NavigableMap 為參考型別，實作類別為 TreeMap
    // TreeMap 底層是紅黑樹，會根據 Key 的內容進行自動排序
    // 因為 Key 是 String，具備自然排序（Natural Order），所以會按字母 A-Z 排序
    // 用途：使用 NavigableMap 宣告，是為了保留後續呼叫 headMap()、tailMap() 等強大區間檢索方法的彈性
    private static NavigableMap<String, Student> students = new TreeMap<>();

    public static void main(String[] args) {

        Course jmc = new Course("jmc101", "Java Master Class",
                "Java");

        Course python = new Course("pyt101", "Python Master Class",
                "Python");

        // 第一階段：加入前 5 筆購買紀錄，此時只有 Mary 購買兩門課
        addPurchase("Mary Martin", jmc, 129.99);

        addPurchase("Andy Martin", jmc, 139.99);

        addPurchase("Mary Martin", python, 149.99);

        addPurchase("Joe Jones", jmc, 149.99);

        addPurchase("Bill Brown", python, 119.99);

        // 第二階段：擴大測試數據，再加入 5 位新學生，並將隨機日期範圍擴大至 1-14 天
        addPurchase("Chuck Cheese", python, 119.99);

        addPurchase("Davey Jones", jmc, 139.99);

        addPurchase("Eva East", python, 139.99);

        addPurchase("Fred Forker", jmc, 139.99);

        addPurchase("Greg Brady", python, 129.99);

        // 測試 1：列印購買紀錄 (LinkedHashMap)
        // 預期結果：控制台輸出的順序會與上面 addPurchase 的呼叫順序完全一致（插入順序）
        purchases.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println("-----------------------");

        // 測試 2：列印學生名單 (TreeMap)
        // 預期結果：雖然我們是先加 Mary 再加 Andy，但輸出時會自動變成 Andy -> Bill -> Chuck... 的英文字母 A-Z 排序
        students.forEach((key, value) -> System.out.println(key + ": " + value));

        // 3. 多維度重組：建立一個以「日期 (LocalDate)」為鍵的 TreeMap
        // 觀念：LocalDate 內部已實作 Comparable，TreeMap 會自動將資料依照「時間軸（Chronological
        // Order）」由遠到近排序
        // 用途：將原本以「課程_學生」為 Key 的流水帳，轉換為「每日銷售報告（1/1 賣了什麼、1/2 賣了什麼）」。
        NavigableMap<LocalDate, List<Purchase>> datedPurchases = new TreeMap<>();

        for (Purchase p : purchases.values()) {

            // 利用 compute 方法動態維護 Map 內部的 List 集合
            // compute: 更新 Map 的 value，並且可以依照舊值決定新值
            datedPurchases.compute(p.purchaseDate(),

                    (pdate, plist) -> {

                        // 如果是該日期的第一筆訂單(plist為null)，就開一個新的 ArrayList
                        // 如果該日期原本就有別人的訂單，就沿用原本的 plist
                        List<Purchase> list = (plist == null) ? new ArrayList<>() : plist;

                        // 不管是不是新的 List，都把當前的訂單 p 加進去
                        list.add(p);

                        // 回傳這個 list，這時 compute 會自動執行 map.put(pdate, list)
                        return list;

                    });

        }

        datedPurchases.forEach((key, value) -> System.out.println(key + ": " + value));

        int currentYear = LocalDate.now().getYear();

        LocalDate firstDay = LocalDate.ofYearDay(currentYear, 1);

        // LocalDate 提供了 plusDays、plusWeeks、minusDays 等方法
        LocalDate week1 = firstDay.plusDays(7);

        // 用 TreeMap 區間截取功能（headMap 和 tailMap）

        // 截取第一週數據：使用 headMap()
        // 觀念：headMap(K) 代表獲取從「起點」到「K 之前」的數據
        // 重點：預設是【不包含 (Exclusive)】傳入的 week1 (1月8日)
        // 邏輯說明：因此這行程式碼會撈出 1/1 ~ 1/7 的所有購買紀錄，完美符合「第一週」的定義。
        Map<LocalDate, List<Purchase>> week1Purchases = datedPurchases.headMap(week1);

        // 截取第二週數據：使用 tailMap()
        // 觀念：tailMap(K) 代表獲取從「K 之後」一直到「終點」的所有數據
        // 重點：預設是【包含 (Inclusive)】傳入的 week1 (1月8日)
        // 邏輯說明：因此這行程式碼會撈出 1/8 以後（包含 1/8 當天）的所有紀錄，作為「第二週」的起點
        Map<LocalDate, List<Purchase>> week2Purchases = datedPurchases.tailMap(week1);

        // System.out.println("-----------------------");
        // week1Purchases.forEach((key, value) -> System.out.println(key + ": " +
        // value));

        // System.out.println("-----------------------");
        // week2Purchases.forEach((key, value) -> System.out.println(key + ": " +
        // value));

        displayStats(1, week1Purchases);

        displayStats(2, week2Purchases);

        System.out.println("-----------------------");

        // 1. 設定歷史起跑點：直接抓取所有銷售紀錄中「最後一天（最新的一天）」的日期
        LocalDate lastDate = datedPurchases.lastKey();

        // 2. 獲取最後一個「組合 (Entry)」，包含該日期的 Key 與存放訂單的 Value 清單
        var previousEntry = datedPurchases.lastEntry();

        // 3. 進入逆向遍歷迴圈：只要還有「前一天的資料（不為 null）」，就繼續往前倒查
        // 觀念：當指針退到地圖中最舊的第一筆資料後，下一次再往前找就會拿到 null，這時迴圈就會安全結束
        while (previousEntry != null) {

            // 從目前的 Entry 中取出當天所有的購買紀錄 List
            List<Purchase> lastDaysData = previousEntry.getValue();

            // 輸出當天日期與該日的銷售總筆數
            System.out.println(lastDate + " purchases : " + lastDaysData.size());

            // 4. 指針推進關鍵：尋找「嚴格小於 (Less Than)」當前日期的下一個最新日期
            // 觀念：lowerKey(K) 會在 TreeMap 中由右往左找，抓出第一個比 K 還要小的 Key（更早的一天）
            LocalDate prevDate = datedPurchases.lowerKey(lastDate);

            // 同理，抓出比當前日期還要小的上一個 Entry 實體
            previousEntry = datedPurchases.lowerEntry(lastDate);

            lastDate = prevDate;

        }

        System.out.println("-----------------------");

        // 1. 建立反向視圖 (descendingMap)
        // 觀念：沒有複製任何資料！它把原本由舊到新的 TreeMap 原地水平翻轉
        // 重點：因為是倒序，所以對 reversed 來說，它的「第一筆 (firstKey)」就是最晚、最新的一天
        var reversed = datedPurchases.descendingMap();

        LocalDate firstDate = reversed.firstKey();

        // 2. 【實驗對比：唯讀 vs 破壞】
        // 原本寫法：var nextEntry = reversed.firstEntry(); -> 只是「偷看」第一筆資料，原創 Map 安然無恙
        // 改良寫法：使用 pollFirstEntry()
        // 警告：這不只是拿資料！它的意思是「把目前的第一筆資料徹底從 Map 中刪除」

        // var nextEntry = reversed.firstEntry();
        var nextEntry = reversed.pollFirstEntry();

        while (nextEntry != null) {

            List<Purchase> lastDaysData = nextEntry.getValue();

            System.out.println(firstDate + " purchases : " + lastDaysData.size());

            // 3. 原本的推進邏輯（已被註解）：
            // 觀念：原本想用 higherKey 找出「比當前更晚(也就是原圖更早)」的日期來推進指針
            // nextEntry = reversed.higherEntry(firstDate);
            LocalDate nextDate = reversed.higherKey(firstDate);

            // 4. 改良後的推進邏輯：直接再次刪除目前的第一筆
            // 觀念：因為前一筆已經被 poll 刪掉了，所以原本的「第二筆」會自動遞補變成「新第一筆」
            // 這樣寫雖然讓程式碼變得非常乾淨、不需要傳任何參數，但卻是會有 bug 的...
            nextEntry = reversed.pollFirstEntry();

            firstDate = nextDate;

        }

        System.out.println("-----------------------");

        // 5. 列印原始 Map
        // 實驗結果：這裡會「什麼都印不出來」一片空白
        // 原因：
        // A. pollFirstEntry() 具有【突變 (Mutating)】內部資料的副作用
        // B. reversed 只是 datedPurchases 的【視圖 (View)】，兩者共用同一份底層記憶體
        // 當我們在迴圈中把 reversed 抽乾的同時，原始的 datedPurchases 也被我們親手徹底清空了
        datedPurchases.forEach((key, value) -> System.out.println(key + ": " + value));

    }

    // 處理學生報名與購買紀錄的連動
    private static void addPurchase(String name, Course course, double price) {

        // 檢查該學生是否已經在 TreeMap 中
        Student existingStudent = students.get(name);

        if (existingStudent == null) {

            existingStudent = new Student(name, course);

            students.put(name, existingStudent);

        } else {

            existingStudent.addCourse(course);

        }

        int day = new Random().nextInt(1, 15);

        String key = course.courseId() + "_" + existingStudent.getId();

        int year = LocalDate.now().getYear();

        Purchase purchase = new Purchase(course.courseId(),
                existingStudent.getId(), price, year, day);

        purchases.put(key, purchase);

    }

    private static void displayStats(int period,
            Map<LocalDate, List<Purchase>> periodData) {

        System.out.println("-----------------------");

        Map<String, Integer> weeklyCounts = new TreeMap<>();

        periodData.forEach((key, value) -> {

            System.out.println(key + ": " + value);

            for (Purchase p : value) {

                // 【核心重點】：利用 merge 進行計數器操作
                // 參數 1：p.courseId() -> 要統計的「key」（課程ID）
                // 參數 2：1 -> 如果是第一次出現，要給予的「初始值」
                // 參數 3：Lambda -> 如果早就存在，該如何處理「舊值(prev)」與「新傳入值(current)」
                weeklyCounts.merge(p.courseId(), 1, (prev, current) -> {

                    return prev + current;

                });

            }

        });

        System.out.printf("Week %d Purchases = %s%n", period, weeklyCounts);

    }

}