// 測試傳統類別的漏洞場景
package Immutable;

public class Main {

    public static void main(String[] args) {

        // =====================================================
        // 建立 Person 物件
        // =====================================================

        Person jane = new Person("Jane", "01/01/1930");
        Person jim = new Person("Jim", "02/02/1932");
        Person joe = new Person("Joe", "03/03/1934");

        // 宣告陣列並傳入 constructor
        Person[] johnsKids = { jane, jim, joe };

        Person john = new Person(
                "John",
                "05/05/1900",
                johnsKids);

        System.out.println(john);

        // =====================================================
        // 實驗 1：setKids 直接替換整個陣列
        // =====================================================

        john.setKids(new Person[] {
                new Person("Ann", "04/04/1930")
        });

        System.out.println(john);

        // =====================================================
        // ⚠️ 實驗 2：Getter 外洩（Reference Leak）
        // =====================================================

        Person[] kids = john.getKids();

        // ❌ 直接透過外部 reference 修改內部資料
        //
        // 因為 getKids() 回傳的是「同一個陣列位址」
        // 所以這裡等於直接操作 john 的內部狀態
        kids[0] = jim;

        System.out.println(john);

        // =====================================================
        // 實驗 3：切斷外部 reference
        // =====================================================

        kids = null;

        // 這不影響 john 本身
        // 因為只是把「外部變數」改成 null
        System.out.println(john);

        // =====================================================
        // 實驗 4：透過 setter 清空 kids
        // =====================================================

        // 此時 kids 已經是 null
        // 等同於：
        // john.setKids(null);
        john.setKids(kids);

        System.out.println(john);
    }
}
