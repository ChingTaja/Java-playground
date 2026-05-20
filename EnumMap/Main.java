package EnumMap;

import java.util.*;

public class Main {

    // 1. 定義星期的列舉（Enum）
    // 每個列舉常數內部都自帶一個從 0 開始的序號（Ordinal）
    // SUNDAY(0), MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5),
    // SATURDAY(6)

    enum WeekDay {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }

    public static void main(String[] args) {

        List<WeekDay> annsWorkDays = new ArrayList<>(List.of(
                WeekDay.MONDAY,
                WeekDay.TUESDAY,
                WeekDay.THURSDAY,
                WeekDay.FRIDAY));

        // 2. 創造型方法（Creational Method / 靜態工廠方法）：copyOf
        // 觀念：EnumSet 是抽象類別 (Abstract)，無法直接 new
        // 傳入任何 Collection，它會自動幫我們實例化
        var annsDaysSet = EnumSet.copyOf(annsWorkDays);

        // 測試 1：印出其實際的實作類別名稱
        // 預期結果：RegularEnumSet
        // 原理解析：EnumSet 底層是【位元向量 (Bit Vector)】
        // 當列舉數量 ≤ 64 時，用 long(64bit) 表示所有元素存在與否
        // > 64 時會變成 JumboEnumSet
        System.out.println(annsDaysSet.getClass().getSimpleName());

        // 測試 2：走訪並列印 Ann 的工作日
        // 會依 Enum 宣告順序輸出（MONDAY, TUESDAY, THURSDAY, FRIDAY）
        annsDaysSet.forEach(System.out::println);

        // 3. 工廠方法：allOf
        // 用途：取得該 Enum 的所有常數
        var allDaysSet = EnumSet.allOf(WeekDay.class);

        System.out.println("---------------------");
        allDaysSet.forEach(System.out::println);

        // 4. 工廠方法：complementOf
        // 全集 - annsDaysSet
        // 用途：找出補集（空檔）
        // 預期結果：SUNDAY, WEDNESDAY, SATURDAY
        Set<WeekDay> newPersonDays = EnumSet.complementOf(annsDaysSet);

        System.out.println("---------------------");
        newPersonDays.forEach(System.out::println);

        // 5. 對比實驗：removeAll（手動補集）
        Set<WeekDay> anotherWay = EnumSet.copyOf(allDaysSet);
        anotherWay.removeAll(annsDaysSet);

        System.out.println("---------------------");
        anotherWay.forEach(System.out::println);

        // 6. 工廠方法：range（範圍截取）
        // 閉區間（包含起點與終點）
        Set<WeekDay> businessDays = EnumSet.range(WeekDay.MONDAY, WeekDay.FRIDAY);

        System.out.println("---------------------");
        businessDays.forEach(System.out::println);

        // 7. EnumMap 特化 Map
        // 底層用 array + ordinal，效率極高
        Map<WeekDay, String[]> employeeMap = new EnumMap<>(WeekDay.class);

        employeeMap.put(WeekDay.FRIDAY, new String[] { "Ann", "Mary", "Bob" });
        employeeMap.put(WeekDay.MONDAY, new String[] { "Mary", "Bob" });

        // 觀念：EnumMap 會依 Enum 順序輸出（MONDAY → SUNDAY）
        employeeMap.forEach(
                (k, v) -> System.out.println(k + " : " + Arrays.toString(v)));
    }
}