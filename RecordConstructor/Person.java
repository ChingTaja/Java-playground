// Record constructor
package RecordConstructor;

/**
 * 💡 宣告一個 Record 類別，其內含兩個組成元件（Components）：
 * name 與 dob
 *
 * 編譯器會自動為我們補上：
 * - private final 欄位
 * - accessor methods（name()、dob()）
 * - equals()
 * - hashCode()
 * - toString()
 */
public record Person(String name, String dob) {

    // =====================================================
    // 形式 A：標準建構子（Canonical / Long Constructor）
    // =====================================================

    /*
     * 指出：
     * 它的參數列表與順序必須與 Record Components 完全相同。
     *
     * 如果手動宣告 Canonical Constructor，
     * 你必須自行完成所有欄位的初始化。
     *
     * ⚠️ 若漏掉任何欄位：
     * this.dob = dob;
     *
     * 編譯器會報錯：
     * might not be initialized in canonical constructor
     *
     * ⚠️ 一旦 final 欄位完成指派後，
     * 再次對該欄位賦值也會報錯。
     */

    /*
     * public Person(String name, String dob) {
     * this.name = name;
     * this.dob = dob.replace('-', '/');
     * }
     */

    // =====================================================
    // 形式 B：自訂建構子（Custom / Overloaded Constructor）
    // =====================================================

    /**
     * 複製建構子（Copy Constructor）
     *
     * 鐵律：
     * 所有非 Canonical Constructor
     * 第一行都必須呼叫 this(...)
     * 將初始化工作委派給 Canonical Constructor。
     */
    public Person(Person p) {

        this(p.name, p.dob);
    }

    // =====================================================
    // 形式 C：精簡建構子（Compact Constructor）
    // =====================================================

    /**
     * Record 專屬語法。
     *
     * 特徵：
     * - 沒有參數列表
     * - 沒有 this.name = name;
     * - 沒有 this.dob = dob;
     *
     * 編譯器會自動補上欄位指派。
     *
     * 適合用途：
     * - 驗證資料（Validation）
     * - 正規化資料（Normalization）
     */
    public Person{

    if(dob==null){throw new IllegalArgumentException("Bad data");}

    // 正規化日期格式
    dob=dob.replace('-','/');}
}