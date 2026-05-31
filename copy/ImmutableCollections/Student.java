package copy.ImmutableCollections;

public class Student {

    // =====================================================
    // 基本不可變欄位（Reference 不可變）
    // =====================================================

    private final String name;

    /**
     * ⚠️ 關鍵陷阱：
     *
     * final ≠ immutable
     *
     * StringBuilder 本身是 mutable（可變物件）
     *
     * 所以：
     * - reference 不能改指向
     * - 但內容可以被修改（append / setLength）
     */
    private final StringBuilder studentNotes;

    // =====================================================
    // Constructor（目前有漏洞）
    // =====================================================

    public Student(String name, StringBuilder studentNotes) {

        this.name = name;

        // ❌ 問題點：
        // 直接共享外部 reference
        //
        // 結果：
        // 外部仍可透過原始 StringBuilder 修改內部狀態
        this.studentNotes = studentNotes;
    }

    // =====================================================
    // Getter（同樣有漏洞）
    // =====================================================

    public String getName() {
        return name;
    }

    /**
     * ❌ 危險 getter：
     *
     * 直接回傳 mutable object reference
     *
     * 外部可以：
     * getStudentNotes().append(...)
     *
     * → 直接破壞封裝（Encapsulation）
     */
    public StringBuilder getStudentNotes() {
        return studentNotes;
    }

    // =====================================================
    // toString
    // =====================================================

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", studentNotes=" + studentNotes +
                '}';
    }
}