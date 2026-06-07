package StreamIntermediate.Engagement;

public record Course(
        String courseCode,
        String title,
        int lectureCount) {

    // =========================
    // Compact Constructor
    // =========================
    //
    // 用來進行參數驗證
    //
    // 我們絕對不希望 lectureCount <= 0
    //
    // 否則後續在計算：
    //
    // 「完成百分比」
    //
    // 時可能引發：
    //
    // Divide by zero error（除以零錯誤）

    public Course{

    if(lectureCount<=0){lectureCount=1;}}

    // =========================
    // Custom Constructor
    // =========================

    public Course(String courseCode, String title) {

        this(courseCode, title, 40);
    }

    @Override
    public String toString() {

        return "%s %s"
                .formatted(courseCode, title);
    }
}