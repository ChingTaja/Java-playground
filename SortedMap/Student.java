package SortedMap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

record Course(String courseId, String name, String subject) {
}

record Purchase(String courseId, int studentId, double price, int yr, int dayOfYear) {

    public LocalDate purchaseDate() {

        // 利用 Java 內建的 LocalDate.ofYearDay 將年份與第幾天，還原成標準的日期物件
        // 用途：方便 TreeMap 後續能以「天」為單位進行時間軸排序
        return LocalDate.ofYearDay(yr, dayOfYear);

    }

}

public class Student {

    // 全域靜態變數，用來發放唯一的學生證號
    public static int lastId = 1;

    private String name;
    private int id;
    private List<Course> courseList;

    // 負責處理真正的初始化與邏輯核心
    public Student(String name, List<Course> courseList) {

        this.name = name;
        this.courseList = courseList;

        // 分配完目前號碼後自動加 1，確保下一個學生不會拿到重複的 ID
        id = lastId++;

    }

    // 提供便利的捷徑，方便「只有一門課」的學生建立
    public Student(String name, Course course) {

        this(name, new ArrayList<>(List.of(course)));

    }

    public String getName() {

        return name;

    }

    public int getId() {

        return id;

    }

    public void addCourse(Course course) {

        courseList.add(course);

    }

    @Override
    public String toString() {

        // 觀念：利用 Arrays.setAll 高效地將 Course 物件陣列扁平化為僅含「課程名稱」的字串陣列
        String[] courseNames = new String[courseList.size()];

        Arrays.setAll(courseNames, i -> courseList.get(i).name());

        return "[%d] : %s".formatted(id, String.join(", ", courseNames));

    }

}