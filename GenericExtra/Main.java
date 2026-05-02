package GenericExtra;

import java.util.ArrayList;
import java.util.List;

public class Main {

    /*
    你會這樣想：
    LPAStudent 是 Student ✅
    
    所以：
    List<LPAStudent> 應該也是 List<Student> ✅（直覺）
    👉 但這一條是 錯的
    
    
    List<LPAStudent> → List<Student> 👉 直接禁止
    
    ✅ 那為什麼這個可以？
    
    List<Student> students = new ArrayList<>();
    students.add(new LPAStudent()); // OK
    
    因為：
    
    👉 LPAStudent 是 Student
    
    👉 放進去的「物件」是合法的
    
    ⚠️ 但容器本身不一樣
    
    ✅ 物件有繼承關係
    ❌ 容器沒有繼承關係
    */

    /*
    容器就是：
    
    List<Student> students = new ArrayList<>();List<LPAStudent> lpaStudents = new ArrayList<>();
    
    👉 這兩個 List / ArrayList 就是「容器」
    LPAStudent 是 Student ✅
    
    但：
    裝 LPAStudent 的盒子 ≠ 裝 Student 的盒子 ❌
    */

    /*
    
    當作為「參考型別（reference types）」使用時：
    
    一個容器（container）裝某種類型，和另一個裝不同類型的容器之間，沒有任何關係
    即使這兩個類型本身是有繼承關係的。
    */

    public static void main(String[] args) {

        int studentCount = 10;
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < studentCount; i++) {
            students.add(new Student());
        }
        students.add(new LPAStudent());
        // printList(students);
        printMoreLists(students);

        List<LPAStudent> lpaStudents = new ArrayList<>();
        for (int i = 0; i < studentCount; i++) {
            lpaStudents.add(new LPAStudent());
        }
        // printList(lpaStudents);
        printMoreLists(lpaStudents);
    }

    // warning:  List is a raw type. References to generic type List<E> should be parameterized
    //

    // public static void printList(List students) {

    //     for (var student : students) {
    //         System.out.println(student);
    //     }
    //     System.out.println();
    // }

    // public static <T extends Student> void printMoreLists(List<T> students) {

    //     for (var student : students) {
    //         System.out.println(student);
    //     }
    //     System.out.println();
    // }


    public static void printMoreLists(List<? extends Student> students) {

        for (var student : students) {
            System.out.println(student);
        }
        System.out.println();
    }

    
    // 你以為可以 overload，但其實不行
    // Type Erasure : Java 編譯後會「把泛型拿掉」(泛型只存在於編譯期，執行時會被擦掉)
    // public static void testList(List<String> list) {

    // }

    // public static void testList(List<Integer> list) {
        
    // }
}