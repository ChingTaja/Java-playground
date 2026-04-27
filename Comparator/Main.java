package Comparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Integer five = 5;
        Integer[] others = { 0, 5, 10, -50, 50 };

        for (Integer i : others) {
            int val = five.compareTo(i); // 回傳 -1 or  0 or 1
            System.out.printf("%d %s %d: compareTo=%d%n", five,
                    (val == 0 ? "==" : (val < 0) ? "<" : ">"), i, val);
        }

        String banana = "banana";
        String[] fruit = { "apple", "banana", "pear", "BANANA" };

        for (String s : fruit) {
            int val = banana.compareTo(s); // String 不僅回傳正負號，還回傳字元間的 Unicode 差值
            System.out.printf("%s %s %s: compareTo=%d%n", banana,
                    (val == 0 ? "==" : (val < 0) ? "<" : ">"), s, val);
        }

        Arrays.sort(fruit);
        System.out.println(Arrays.toString(fruit));

        System.out.println("A:" + (int) 'A' + " " + "a:" + (int) 'a');
        System.out.println("B:" + (int) 'B' + " " + "b:" + (int) 'b');
        System.out.println("P:" + (int) 'P' + " " + "p:" + (int) 'p');

        Student tim = new Student("Tim");
        Student[] students = { new Student("Zach"), new Student("Tim"),
                new Student("Ann") };

        Arrays.sort(students);
        System.out.println(Arrays.toString(students));

        System.out.println("result = " + tim.compareTo(new Student("Taja")));

    }
}

class StudentGPAComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        return (o1.gpa + o1.name).compareTo(o2.gpa + o2.name);
    }
}

// 程式碼問題：class Student implements Comparable（沒加 <Student>） 的版本
// 對應下方 public int compareTo(Object o)
// 必須手動轉型

// 型別不安全：像測試的 tim.compareTo("Taja")
//編譯器不會攔截，但執行時會因為無法將 String 轉為 Student 而噴出 ClassCastException

// 1. 加上 <Student>
class Student implements Comparable<Student> {
    private static int LAST_ID = 1000;
    private static Random random = new Random();

    // 就是把 name 的存取權限改成 protected 或 package-private（預設）
    //我會把它改成 package-private，也就是不寫任何存取修飾子

    // protected = 同 package + 子類都能用
    // package-private = 只有同 package 能用
    String name;
    private int id;
    protected double gpa;

    public Student(String name) {
        this.name = name;
        id = LAST_ID++;
        gpa = random.nextDouble(1.0, 4.0);
    }

    @Override
    public String toString() {
        return "%d - %s (%.2f)".formatted(id, name, gpa);
    }

    // @Override
    // public int compareTo(Object o) {
    //     Student other = (Student) o;
    //     return name.compareTo(other.name);
    // }

    // 2. 參數型別直接改為 Student，不再是 Object
    @Override
    public int compareTo(Student o) {
        // 3. 直接使用，不再需要手動強制轉型 (Student) o

        // return name.compareTo(o.name);
        return Integer.valueOf(id).compareTo(Integer.valueOf(o.id));
    }
}

// 7:10



