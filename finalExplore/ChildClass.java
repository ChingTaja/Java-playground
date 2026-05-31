// 模擬子類別的行為與衝突
package finalExplore;

import finalExplore.generic.BaseClass;

public class ChildClass extends BaseClass {

    // =====================================================
    // A：正確的自訂擴充
    // =====================================================

    // 子類別設計者遵照規範，
    // 在執行父類別原本邏輯之前，
    // 加入自己的額外功能（extra stuff）。
    @Override
    protected void optionalMethod() {

        System.out.println(
                "[ChildClass.optionalMethod]: child, extra stuff here!");

        super.optionalMethod();
    }

    /*
     * =====================================================
     * B：不看規範的行為（已被 final 封鎖）
     * =====================================================
     *
     * 如果 BaseClass.recommendedMethod() 沒有加 final，
     * 子類別就能直接覆寫整個流程。
     *
     * 問題在於：
     * 子類別可能忘記呼叫 super.recommendedMethod()
     * 或故意跳過某些必要步驟。
     *
     * 後果：
     * BaseClass 的 mandatoryMethod()
     * 永遠不會被執行。
     *
     * 因此父類別使用 final：
     * 強制所有子類別遵守既定流程。
     *
     * @Override
     * public void recommendedMethod() {
     * System.out.println(
     * "[ChildClass]: I'll do things my way!");
     *
     * optionalMethod();
     * }
     */

    // =====================================================
    // C：private 方法的常見誤解
    // =====================================================

    // 即使子類別宣告了一個名稱完全相同的方法：
    //
    // private void mandatoryMethod()
    //
    // 它也不是 Override。
    //
    // 原因：
    // BaseClass.mandatoryMethod() 是 private，
    // 子類別根本看不到它。
    //
    // 因此這裡只是宣告了一個全新的方法，
    // 與父類別毫無關係。
    //
    // 當 BaseClass.recommendedMethod() 執行時：
    //
    // mandatoryMethod();
    //
    // 永遠呼叫的是：
    //
    // BaseClass.mandatoryMethod()
    //
    // 而不是這裡的方法。
    private void mandatoryMethod() {

        System.out.println(
                "[ChildClass.mandatoryMethod]: my own important stuff.");
    }

    // =====================================================
    // Static Method Hiding
    // =====================================================

    // 這不是 Override，而是 Method Hiding。
    //
    // 靜態方法屬於 Class，
    // 不屬於 Object。
    //
    // 因此：
    //
    // - Instance Method → Override
    // - Static Method → Hide
    //
    // 不能加上 @Override，
    // 否則編譯失敗。
    public static void recommendedStatic() {

        System.out.println(
                "[ChildClass.recommendedStatic]: BEST Way to Do it");

        optionalStatic();

        // 無法呼叫：
        //
        // mandatoryStatic();
        //
        // 因為 BaseClass.mandatoryStatic()
        // 是 private static，
        // 子類別完全不可見。
    }
}