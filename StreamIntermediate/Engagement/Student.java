package StreamIntermediate.Engagement;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Student {

    // =======================================
    // 靜態欄位 (Static Fields)
    // =======================================

    // 用於自動遞增分配 studentId，
    // 確保每位新學生 ID 唯一

    private static long lastStudentId = 1;

    // 宣告為 private static final 的隨機物件，
    // 供所有隨機數據生成共用

    private static final Random random = new Random();

    // ============================
    // 實例欄位 (Instance Fields)
    // ============================

    // 所有欄位皆設為 private final，
    // 確保物件建立後的基本資料不被竄改

    private final long studentId;

    private final String countryCode;

    private final int yearEnrolled;

    private final int ageEnrolled;

    private final String gender;

    private final boolean programmingExperience;

    // 使用 HashMap 儲存學生的課程參與紀錄
    //
    // 由於只是透過 courseCode 作為 Key 查找，
    // 不需要維持插入順序

    private final Map<String, CourseEngagement> engagementMap = new HashMap<>();

    // =======================================
    // Constructor
    // =======================================
    //
    // 接受：
    //
    // - 基本學生資料
    // - 可變引數 courses（varargs）
    //
    // 可一次傳入：
    //
    // 0 ~ 多門課程

    public Student(
            String countryCode,
            int yearEnrolled,
            int ageEnrolled,
            String gender,
            boolean programmingExperience,
            Course... courses) {

        studentId = lastStudentId++;

        this.countryCode = countryCode;
        this.yearEnrolled = yearEnrolled;
        this.ageEnrolled = ageEnrolled;
        this.gender = gender;
        this.programmingExperience = programmingExperience;

        // 使用 Enhanced For Loop
        // 逐一處理傳入課程
        //
        // 預設：
        //
        // 入學當年 1 月 1 日註冊

        for (Course course : courses) {

            addCourse(
                    course,
                    LocalDate.of(yearEnrolled, 1, 1));
        }
    }

    // =======================
    // Method Overloading
    // =======================

    // Overloading A
    //
    // 不指定日期時：
    //
    // -> 預設 LocalDate.now()

    public void addCourse(Course newCourse) {

        addCourse(newCourse, LocalDate.now());
    }

    // Overloading B
    //
    // 核心方法：
    //
    // 建立 CourseEngagement
    // 並存入 Map

    public void addCourse(
            Course newCourse,
            LocalDate enrollDate) {

        engagementMap.put(
                newCourse.courseCode(),

                new CourseEngagement(
                        newCourse,
                        enrollDate,
                        "Enrollment"));
    }

    // ==================
    // Getters 與封裝
    // ==================

    public long getStudentId() {

        return studentId;
    }

    public String getCountryCode() {

        return countryCode;
    }

    public int getYearEnrolled() {

        return yearEnrolled;
    }

    public int getAgeEnrolled() {

        return ageEnrolled;
    }

    public String getGender() {

        return gender;
    }

    // 將：
    //
    // isProgrammingExperience()
    //
    // 改為語意更自然的：
    //
    // hasProgrammingExperience()

    public boolean hasProgrammingExperience() {

        return programmingExperience;
    }

    // ⚠️ 絕對不要直接回傳 engagementMap
    //
    // 否則外部程式可直接修改內部狀態
    //
    // 因此：
    //
    // 使用 Defensive Copy（防禦性拷貝）

    public Map<String, CourseEngagement> getEngagementMap() {

        return Map.copyOf(engagementMap);
    }

    // ===================
    // Calculated Fields
    // ===================

    public int getYearsSinceEnrolled() {

        return LocalDate.now().getYear()
                - yearEnrolled;
    }

    public int getAge() {

        return ageEnrolled
                + getYearsSinceEnrolled();
    }

    // overload A
    //
    // 查詢：
    //
    // 特定課程未活動月數

    public int getMonthsSinceActive(
            String courseCode) {

        CourseEngagement info = engagementMap.get(courseCode);

        return info == null
                ? 0
                : info.getMonthsSinceActive();
    }

    // overload B
    //
    // 查詢：
    //
    // 所有課程中
    // 最近一次活動距今的月份

    public int getMonthsSinceActive() {

        int inactiveMonths = (LocalDate.now().getYear() - 2014)
                * 12;

        for (String key : engagementMap.keySet()) {

            inactiveMonths = Math.min(
                    inactiveMonths,
                    getMonthsSinceActive(key));
        }

        return inactiveMonths;
    }

    public double getPercentComplete(
            String courseCode) {

        var info = engagementMap.get(courseCode);

        return (info == null)
                ? 0
                : info.getPercentComplete();
    }

    // ==================
    // 商務邏輯方法
    // ==================

    public void watchLecture(
            String courseCode,
            int lectureNumber,
            int month,
            int year) {

        var activity = engagementMap.get(courseCode);

        if (activity != null) {

            activity.watchLecture(
                    lectureNumber,
                    LocalDate.of(year, month, 1));
        }
    }

    // ==========================================
    // Static Factory Method
    // ==========================================

    // 輔助方法：
    //
    // 從字串陣列中隨機挑一個值

    private static String getRandomVal(
            String... data) {

        return data[random.nextInt(data.length)];
    }

    // 工廠方法：
    //
    // 隨機生成 Student
    //
    // 用來測試 Stream Pipeline

    public static Student getRandomStudent(
            Course... courses) {

        int maxYear = LocalDate.now().getYear() + 1;

        Student student = new Student(

                getRandomVal(
                        "AU",
                        "CA",
                        "CN",
                        "GB",
                        "IN",
                        "UA",
                        "US"),

                random.nextInt(2015, maxYear),

                random.nextInt(18, 90),

                getRandomVal("M", "F", "U"),

                random.nextBoolean(),

                courses);

        for (Course c : courses) {

            int lecture = random.nextInt(
                    1,
                    c.lectureCount());

            int year = random.nextInt(
                    student.getYearEnrolled(),
                    maxYear);

            int month = random.nextInt(1, 13);

            // 防錯檢查：
            //
            // 若年份是今年，
            // 月份不可超過當前月份

            if (year == (maxYear - 1)) {

                if (month > LocalDate.now()
                        .getMonthValue()) {

                    month = LocalDate.now()
                            .getMonthValue();
                }
            }

            student.watchLecture(
                    c.courseCode(),
                    lecture,
                    month,
                    year);
        }

        return student;
    }

    @Override
    public String toString() {

        return "Student{" +
                "studentId=" + studentId +
                ", countryCode='" + countryCode + '\'' +
                ", yearEnrolled=" + yearEnrolled +
                ", ageEnrolled=" + ageEnrolled +
                ", gender='" + gender + '\'' +
                ", programmingExperience="
                + programmingExperience +
                ", engagementMap="
                + engagementMap +
                '}';
    }
}