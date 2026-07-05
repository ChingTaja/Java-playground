package WritingFiles.student;


/* 使用 Java Record 定義唯讀的課程資料結構，內建基本欄位 */
public record Course(String courseCode, String title) {

    /* 取得該課程的總堂數，目前固定回傳 15 堂 */
    public int getLectureCount() {
        return 15;
    }

    @Override
    public String toString() {
        return "%s %s".formatted(courseCode, title);
    }
}