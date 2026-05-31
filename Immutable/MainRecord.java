// 測試 Record 的防禦極限
package Immutable;

public class MainRecord {

    public static void main(String[] args) {

        // =====================================================
        // 建立基本資料
        // =====================================================

        PersonRecord jane = new PersonRecord("Jane", "01/01/1930");
        PersonRecord jim = new PersonRecord("Jim", "02/02/1932");
        PersonRecord joe = new PersonRecord("Joe", "03/03/1934");

        // 原始 kids 陣列
        PersonRecord[] johnsKids = { jane, jim, joe };

        PersonRecord john = new PersonRecord(
                "John",
                "05/05/1900",
                johnsKids);

        System.out.println(john);

        // =====================================================
        // 建立沒有孩子的 copy（內建 20 空間）
        // =====================================================

        PersonRecord johnCopy = new PersonRecord("John", "05/05/1900");

        System.out.println(johnCopy);

        // =====================================================
        // 💡 驗證 1：Getter 防禦性複製
        // =====================================================

        PersonRecord[] kids = johnCopy.kids();

        // ❌ 修改的是「複本」
        kids[0] = jim;
        kids[1] = new PersonRecord("Ann", "04/04/1936");

        // ✔ johnCopy 本體不受影響
        System.out.println(johnCopy);

        // =====================================================
        // ⚠️ 驗證 2：建構子端的漏洞（reference sharing）
        // =====================================================

        // 修改外部原始陣列
        johnsKids[0] = new PersonRecord("Ann", "04/04/1936");

        System.out.println(john);

        // =====================================================
        // ❗ 結論
        // =====================================================
        //
        // 即使使用 record：
        //
        // ✔ 欄位是 final
        // ✔ 沒有 setter
        //
        // 但只要「外部可變物件」被共享：
        //
        // ❌ 仍然可以破壞 immutability
        //
        // =====================================================
        //
        // 真正完全不可變（Fully Immutable）的條件：
        //
        // 1. 欄位 final
        // 2. constructor 做 defensive copy
        // 3. getter 做 defensive copy
        // 4. 內部物件也不可變（或同樣防禦）
        //
    }
}