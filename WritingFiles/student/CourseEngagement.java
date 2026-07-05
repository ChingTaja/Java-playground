package WritingFiles.student;

import java.time.LocalDate;
import java.time.Month;

/* 觀念：紀錄學生對特定課程的參與狀態、報名時間與最後活動紀錄 */
public class CourseEngagement {
    private final String courseCode;
    private String engagementType;
    private final int enrollmentMonth;
    private final int enrollmentYear;

    private int lastLecture;
    private int lastActiveMonth;
    private int lastActiveYear;

    public CourseEngagement(String courseCode, int month, int year, String engagementType) {
        this.courseCode = courseCode;
        enrollmentMonth = lastActiveMonth = month;
        enrollmentYear = lastActiveYear = year;
        this.engagementType = engagementType;
    }

    public String getEngagementType() {
        return engagementType;
    }

    public int getEnrollmentMonth() {
        return enrollmentMonth;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public int getLastLecture() {
        return lastLecture;
    }

    public int getLastActiveMonth() {
        return lastActiveMonth;
    }

    public int getLastActiveYear() {
        return lastActiveYear;
    }

    /* 計算課程完成百分比，以最後觀看的堂數與總堂數進行浮點數運算 */
    public double getPercentComplete(int lectureCount) {
        return lastLecture * 100.0 / lectureCount;
    }

    /* 計算該學生自上次活動以來，處於不活躍狀態（未上課）的累計月份數 */
    public int getInactiveMonths() {

        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();

        int months = (now.getYear() - lastActiveYear) * 12;
        if (currentMonth > lastActiveMonth) {
            months += (currentMonth - lastActiveMonth);
        } else {
            months -= (lastActiveMonth - currentMonth);
        }
        return months;
    }

    @Override
    public String toString() {
        return "%s,%s,%d,%s".formatted(courseCode,
                Month.of(lastActiveMonth), lastActiveYear, engagementType);
    }

    /* 更新最後活動的堂數與年月，並動態改寫參與型態描述 */
    void recordLastActivity(int lectureNumber, int month, int year) {

        if (lectureNumber > lastLecture) {
            lastLecture = lectureNumber;
        }
        lastActiveMonth = month;
        lastActiveYear = year;
        engagementType = "Lecture " + lastLecture;
    }
}