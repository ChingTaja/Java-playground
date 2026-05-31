// 打造真正不可變類別的實作
//  make instance field private and final
package Immutable;

import java.util.Arrays;

public class PersonImmutable {

    // =====================================================
    // 策略 1：欄位不可變（Immutable Fields）
    // =====================================================

    private final String name;
    private final String dob;

    /**
     * ⚠️ 注意：
     * kids 雖然是 final（reference 不可變）
     * 但「陣列內容」仍然是 mutable（可變）
     *
     * final ≠ deep immutable
     */
    protected final PersonImmutable[] kids;

    // =====================================================
    // 主建構子（核心防線）
    // =====================================================

    public PersonImmutable(
            String name,
            String dob,
            PersonImmutable[] kids) {

        this.name = name;
        this.dob = dob;

        // ✅ 防禦性複製（Defensive Copy）
        //
        // 避免外部持有原始 array reference 後修改內容
        this.kids = (kids == null)
                ? null
                : Arrays.copyOf(kids, kids.length);
    }

    // =====================================================
    // 建構子重載
    // =====================================================

    public PersonImmutable(String name, String dob) {
        this(name, dob, null);
    }

    // =====================================================
    // Copy Constructor（安全版本）
    // =====================================================

    /**
     * ⚠️ 如果直接 this.kids = person.kids → shallow copy
     * → 會共享同一個 array reference（危險）
     *
     * ✔ 正確方式：
     * 透過主建構子再次做 defensive copy
     */
    protected PersonImmutable(PersonImmutable person) {

        this(
                person.getName(),
                person.getDob(),
                person.getKids());
    }

    // =====================================================
    // Getter（只讀接口）
    // =====================================================

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    /**
     * ✔ final 的目的：
     * 防止子類別 override 後「直接回傳原始 reference」
     *
     * ✔ 同時回傳 defensive copy
     */
    public final PersonImmutable[] getKids() {

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
                            : kids[i].name);

            kidString = String.join(", ", names);
        }

        return name
                + ", dob = " + getDob()
                + ", kids = " + kidString;
    }
}
