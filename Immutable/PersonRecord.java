// 現代 Record 與防禦性複製
package Immutable;

import java.util.Arrays;

/**
 * 💡 Record 本身天然具備：
 * - private final 欄位
 * - 自動建構子
 * - 無 setter
 *
 * ⚠️ 但如果包含「可變物件」（例如陣列），
 * 仍然不是完全不可變（not fully immutable）
 */
public record PersonRecord(
        String name,
        String dob,
        PersonRecord[] kids) {

    // =====================================================
    // 自訂建構子（Convenience Constructor）
    // =====================================================

    public PersonRecord(String name, String dob) {

        this(name, dob, new PersonRecord[20]);
    }

    // =====================================================
    // 防禦性 Getter（核心重點）
    // =====================================================

    /**
     * ✅ 解決 Record 預設 getter 外洩 reference 的問題
     *
     * 原本自動生成：
     * public PersonRecord[] kids()
     *
     * 會直接回傳內部陣列 reference → 危險
     *
     * 現在改為：
     * defensive copy
     */
    @Override
    public PersonRecord[] kids() {

        // ❌ 危險版本：
        // return kids;

        // ✅ 安全版本：
        return kids == null
                ? null
                : Arrays.copyOf(kids, kids.length);
    }

    // =====================================================
    // toString（安全輸出）
    // =====================================================

    @Override
    public String toString() {

        String kidString = "n/a";

        if (kids != null) {

            String[] names = new String[kids.length];

            Arrays.setAll(
                    names,
                    i -> kids[i] == null
                            ? ""
                            : kids[i].name());

            kidString = String.join(", ", names);
        }

        return name + ", dob = " + dob + ", kids = " + kidString;
    }
}