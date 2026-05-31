package finalExplore.generic;

public class BaseClass {

    // =====================================================
    // Instance Methods
    // =====================================================

    // 禁止覆寫（Override）
    //
    // 防止子類別不遵守既定流程、
    // 不呼叫 super.recommendedMethod()，
    // 導致整個工作流程被破壞。
    public final void recommendedMethod() {

        System.out.println(
                "[BaseClass.recommendedMethod]: Best Way to Do it");

        optionalMethod();
        mandatoryMethod();
    }

    // 設為 protected
    //
    // 表示：
    // 1. 同 package 類別可存取
    // 2. 子類別可存取
    //
    // 子類別可以選擇覆寫此方法，
    // 作為擴充點（Extension Point）
    protected void optionalMethod() {

        System.out.println(
                "[BaseClass.optionalMethod]: Customize Optional Method");
    }

    // 設為 private
    //
    // 子類別完全看不到此方法，
    // 因此不可能覆寫（Override）
    //
    // 測試：
    // private final void mandatoryMethod()
    //
    // IntelliJ 通常會顯示灰色警告：
    // 「final is redundant」
    //
    // 原因：
    // private 已經讓方法失去多型性，
    // 再加 final 沒有額外意義。
    private void mandatoryMethod() {

        System.out.println(
                "[BaseClass.mandatoryMethod]: NON-NEGOTIABLE!");
    }

    // =====================================================
    // Static Methods
    // =====================================================

    public static void recommendedStatic() {

        System.out.println(
                "[BaseClass.recommendedStatic] BEST Way to Do it");

        optionalStatic();
        mandatoryStatic();
    }

    protected static void optionalStatic() {

        System.out.println(
                "[BaseClass.optionalStatic]: Optional");
    }

    private static void mandatoryStatic() {

        System.out.println(
                "[BaseClass.mandatoryStatic]: MANDATORY");
    }
}