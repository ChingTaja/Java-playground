package copy.ImmutableCollections;

import copy.ImmutableCollections.Student;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // =====================================================
        // 可變欄位：StringBuilder（後續會造成 side effect）
        // =====================================================

        StringBuilder bobsNotes = new StringBuilder();

        StringBuilder billsNotes = new StringBuilder("Bill struggles with generics");

        Student bob = new Student("Bob", bobsNotes);
        Student bill = new Student("Bill", billsNotes);

        // =====================================================
        // 原始 List（可變）
        // =====================================================

        List<Student> students = new ArrayList<>(List.of(bob, bill));

        // =====================================================
        // 複製實驗 A：new ArrayList（shallow copy）
        // =====================================================

        List<Student> studentsFirstCopy = new ArrayList<>(students);

        // =====================================================
        // 複製實驗 B：List.copyOf（unmodifiable snapshot）
        // =====================================================

        List<Student> studentsSecondCopy = List.copyOf(students);

        // =====================================================
        // 複製實驗 C：unmodifiableList（unmodifiable view）
        // =====================================================

        List<Student> studentsThirdCopy = Collections.unmodifiableList(students);

        // =====================================================
        // ⚡ 操作與副作用測試
        // =====================================================

        // ✔ 可修改 list（第一份 copy）
        studentsFirstCopy.add(
                new Student("Bonnie", new StringBuilder()));

        studentsFirstCopy.sort(
                Comparator.comparing(Student::getName));

        // 原始 list 也新增
        students.add(
                new Student("Bonnie", new StringBuilder()));

        // =====================================================
        // ⚠️ Mutable object side effect（核心陷阱）
        // =====================================================

        // ❌ 透過 shared reference 修改 StringBuilder
        bobsNotes.append(" Bob was one of my first students.");

        // ❌ 修改 Bonnie notes（透過 getter 拿到 reference）
        StringBuilder bonniesNotes = studentsFirstCopy.get(2).getStudentNotes();

        bonniesNotes.append(" Bonnie is taking 3 of my courses");

        // =====================================================
        // 輸出結果比較
        // =====================================================

        students.forEach(System.out::println);

        System.out.println("-----------------------");

        studentsFirstCopy.forEach(System.out::println);

        System.out.println("-----------------------");

        studentsSecondCopy.forEach(System.out::println);

        System.out.println("-----------------------");

        studentsThirdCopy.forEach(System.out::println);

        System.out.println("-----------------------");
    }
}