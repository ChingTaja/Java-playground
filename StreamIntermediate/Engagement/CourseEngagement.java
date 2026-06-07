package StreamIntermediate.Engagement;

import java.time.LocalDate;
import java.time.Period;

// 這個類別需要保持「某種程度的可變性（Mutable）」
//
// 進度、最後活動日期等都需要更新，
// 但我們仍透過封裝（Encapsulation）
// 來嚴格控制其變更流程

public class CourseEngagement {

    // 課程與註冊日期在初始化後就不該被修改，
    // 因此宣告為 final

    private final Course course;

    private final LocalDate enrollmentDate;

    private String engagementType;

    private int lastLecture;

    private LocalDate lastActivityDate;

    // ==================
    // 建構子 (Constructor)
    // ==================

    public CourseEngagement(
            Course course,
            LocalDate enrollmentDate,
            String engagementType) {

        this.course = course;

        // 初始化時：
        //
        // 將「最後活動日期」
        // 預設為與「註冊日期」相同
        //
        // 這裡巧妙運用了：
        //
        // Assignment Chain（指派鏈）

        this.enrollmentDate = this.lastActivityDate = enrollmentDate;

        this.engagementType = engagementType;
    }

    // ==========================
    // 封裝與自訂 Getter 方法
    // ==========================

    // 不直接暴露整個 Course 物件，
    // 而是對外提供更精準的 getCourseCode()

    public String getCourseCode() {

        return course.courseCode();
    }

    public int getEnrollmentYear() {

        return enrollmentDate.getYear();
    }

    public String getEngagementType() {

        return engagementType;
    }

    public int getLastLecture() {

        return lastLecture;
    }

    public int getLastActivityYear() {

        return lastActivityDate.getYear();
    }

    public String getLastActivityMonth() {

        return "%tb".formatted(lastActivityDate);
    }

    public double getPercentComplete() {

        return lastLecture * 100.0
                / course.lectureCount();
    }

    // 計算：
    //
    // 該學生在這門課上
    // 已經「不活躍（摸魚）」了幾個月
    //
    // 透過：
    //
    // Period.between(start, end)
    //
    // 傳入：
    //
    // 1. 最後活動日
    // 2. 當前日期
    //
    // toTotalMonths()
    // 會回傳：
    //
    // 相差的總月數（long）

    public int getMonthsSinceActive() {

        LocalDate now = LocalDate.now();

        var months = Period
                .between(lastActivityDate, now)
                .toTotalMonths();

        // 月數不可能超過 int 範圍，
        // 因此可安全轉型

        return (int) months;
    }

    void watchLecture(
            int lectureNumber,
            LocalDate currentDate) {

        // 確保觀看進度不會倒退

        lastLecture = Math.max(
                lectureNumber,
                lastLecture);

        lastActivityDate = currentDate;

        engagementType = "Lecture " + lastLecture;
    }

    @Override
    public String toString() {

        return "%s: %s %d %s [%d]"
                .formatted(
                        course.courseCode(),
                        getLastActivityMonth(),
                        getLastActivityYear(),
                        engagementType,
                        getMonthsSinceActive());
    }
}