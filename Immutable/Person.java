//  傳統類別的破壞性實驗
package Immutable;

import java.util.Arrays;

public class Person {

    // =====================================================
    // 封裝欄位（Encapsulation）
    // =====================================================

    private String name;
    private String dob;

    // ⚠️ 致命點：
    // 陣列在 Java 中是 Mutable（可變物件）
    //
    // 即使欄位是 private，
    // 仍可能透過 reference 被外部修改內容
    private Person[] kids;

    public Person(String name, String dob, Person[] kids) {

        this.name = name;
        this.dob = dob;

        // ❌ 問題點：
        // 直接指派外部傳入的 reference
        //
        // 結果：
        // 呼叫端如果修改原本 array，
        // 會直接影響這個物件內部狀態
        this.kids = kids;
    }

    public Person(String name, String dob) {
        this(name, dob, null);
    }

    // =====================================================
    // Getter（安全）
    // =====================================================

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    // ❌ 危險 Getter
    //
    // 直接回傳內部陣列 reference
    //
    // 問題：
    // 外部可以直接修改內容：
    //
    // person.getKids()[0] = new Person(...);
    //
    // → 破壞封裝（Encapsulation）
    public Person[] getKids() {
        return kids;
    }

    // ❌ Setter（完全開洞）
    //
    // 允許外部整個替換陣列 reference
    //
    // 風險：
    // 失去對內部狀態的任何控制
    public void setKids(Person[] kids) {
        this.kids = kids;
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
                    i -> names[i] == null
                            ? ""
                            : kids[i].name);

            kidString = String.join(", ", names);
        }

        return name
                + ", dob = " + dob
                + ", kids = " + kidString;
    }
}
