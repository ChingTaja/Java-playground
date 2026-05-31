package finalExplore;

import finalExplore.generic.BaseClass;
import finalExplore.external.Logger;

public class Main {

    public static void main(String[] args) {

        BaseClass parent = new BaseClass();
        ChildClass child = new ChildClass();

        // 多型引用
        // 宣告的型別（左邊）是 BaseClass，但實際 new 出來的實體（右邊）是 ChildClass
        BaseClass childReferredToAsBase = new ChildClass();

        // =====================================================
        // 【實例方法（Instance Methods）】
        // 運作機制：執行期「動態綁定（Dynamic Binding）」
        // 結論：JVM 在執行時，只看右邊真正被 new 出來的實體是誰
        // =====================================================

        // 呼叫父類別原本的邏輯
        parent.recommendedMethod();

        System.out.println("--------------------");

        // Session 1
        // 變數型別是 BaseClass，但實體是 ChildClass
        // 輸出結果：會印出 "child, extra stuff here"
        // 解析：
        // 因為是實例方法，Java 走多型。
        // JVM 發現記憶體裡實體是 ChildClass，
        // 所以直接執行子類別覆寫（Override）後的方法，
        // 左邊的宣告型別完全不重要。
        childReferredToAsBase.recommendedMethod();

        System.out.println("--------------------");

        // 變數型別是 ChildClass，實體也是 ChildClass
        // 輸出結果：同樣執行子類別覆寫後的方法
        // 與 Session 1 表現完全一致
        child.recommendedMethod();

        System.out.println("--------------------");

        // =====================================================
        // 【靜態方法（Static Methods）】
        // 運作機制：編譯期「靜態綁定（Static Binding）」
        // 核心結論：編譯器在編譯時，只看左邊宣告的變數型別是誰
        // 觀念解密：這不叫覆寫（Override），這叫方法隱藏（Method Hiding）
        // =====================================================

        parent.recommendedStatic();

        System.out.println("--------------------");

        // 【致命對比！】
        // 變數型別是 BaseClass，但實體是 ChildClass
        //
        // 為什麼這行沒有呼叫到 ChildClass 的靜態方法？
        //
        // 因為靜態方法屬於類別（Class），而不是物件（Object）
        //
        // 編譯器在編譯時看到：
        // childReferredToAsBase 的型別是 BaseClass
        //
        // 於是直接轉譯成：
        // BaseClass.recommendedStatic();
        //
        // 完全不理會右邊實際 new 出來的是誰
        childReferredToAsBase.recommendedStatic();

        System.out.println("--------------------");

        // 變數型別是 ChildClass，實體也是 ChildClass
        //
        // 編譯器看到變數型別是 ChildClass
        // 因此轉譯為：
        // ChildClass.recommendedStatic();
        //
        // 這時子類別同名靜態方法成功把父類別方法隱藏（Hide）起來
        child.recommendedStatic();

        // =====================================================
        // final 與參數傳遞
        // =====================================================

        String xArgument = "This is all I've got to say about Section ";

        StringBuilder zArgument = new StringBuilder("Only saying this: Section ");

        doXYZ(xArgument, 16, zArgument);

        // 輸出：
        // This is all I've got to say about Section
        //
        // String 是不可變（Immutable）
        // 方法內重新指定 x 的引用，不影響外部 xArgument
        System.out.println("After Method, xArgument: " + xArgument);

        // 輸出：
        // Only saying this: Section 16
        //
        // Important Distinction:
        // final ≠ Immutable
        //
        // final 只能阻止 Reference 被重新賦值（Reassign）
        // 無法阻止可變物件內容被修改
        System.out.println("After Method, zArgument: " + zArgument);

        // =====================================================
        // StringBuilder 與副作用（Side Effect）
        // =====================================================

        StringBuilder tracker = new StringBuilder("Step 1 is abc");

        // ❌ 危險
        //
        // 直接把 tracker 的 Reference 傳給 Logger
        // 如果 Logger 內部呼叫：
        //
        // sb.setLength(0);
        //
        // 就會直接修改同一塊記憶體
        //
        // Logger.logToConsole(tracker);

        // ✅ 安全
        //
        // tracker.toString()
        //
        // 會建立全新的 String 物件
        // 並把內容複製進去
        //
        // Logger 只能操作這個新的 String
        // 無法影響原本的 StringBuilder
        Logger.logToConsole(tracker.toString());

        tracker.append(", Step 2 is xyz.");

        // Logger.logToConsole(tracker);
        Logger.logToConsole(tracker.toString());

        System.out.println("After logging, tracker = " + tracker);
    }

    /**
     * 【doXYZ 方法：探討 final 在區域變數與方法參數上的本質】
     *
     * @param x
     *          常規參數。
     *          雖然方法內寫了 x = c; 嘗試重新賦值，
     *          但 String 為 Immutable，
     *          且 Java 採用 Pass By Value，
     *          因此完全不影響外部的 xArgument。
     *
     * @param y
     *          區域整數。
     *
     * @param z
     *          final StringBuilder（Mutable Object）。
     *
     *          final z 代表：
     *          z 這個 Reference 被鎖死了，
     *          不能改指向其他物件。
     *
     *          但仍可透過這個 Reference
     *          修改物件內部狀態。
     */
    private static void doXYZ(
            String x,
            int y,
            final StringBuilder z) {

        final String c = x + y;

        System.out.println("c = " + c);

        // 如果寫：
        // c = "new string";
        //
        // 編譯錯誤：
        // cannot assign a value to final variable c
        //
        // 即使不加 final，
        // 只要後續沒修改，
        // Java 也會視其為 effectively final

        x = c;

        // x 現在指向新的 String
        // 但只存在於此方法作用域內
        // 方法結束即消失

        // =====================================================
        // final 的常見誤解
        // =====================================================

        z.append(y);

        // 為什麼 final z 可以成功 append？
        //
        // 因為 final 鎖定的是 Reference
        // 而不是物件內容
        //
        // z 仍然指向同一個 StringBuilder
        // 只是 StringBuilder 本身是 Mutable
        // 所以內容被成功修改

        // =====================================================
        // final 真正禁止的事情
        // =====================================================

        // z = new StringBuilder("This is a new reference");

        // 如果解除註解：
        //
        // 編譯器立刻報錯
        //
        // 因為你試圖讓 z
        // 指向全新的記憶體位置
        //
        // 這違反 final Reference 的規則
    }
}
