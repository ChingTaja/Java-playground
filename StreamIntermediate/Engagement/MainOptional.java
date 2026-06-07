package StreamIntermediate.Engagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainOptional {

    public static void main(String[] args) {

        Course pymc = new Course("PYMC", "Python Masterclass");

        Course jmc = new Course("JMC", "Java Masterclass");

        /*
         * -----------------------------------------
         * Optional 核心實戰重點：
         * 
         * 1. empty / of / ofNullable 差異
         * 2. isPresent / isEmpty
         * 3. get() 的危險性
         * 4. ifPresent / ifPresentOrElse
         * 5. orElse vs orElseGet（Eager vs Lazy）
         * 6. Optional.map / filter（類 Stream）
         * 7. 永遠避免回傳 null
         * ------------------------------------------
         */

        // 使用 Stream.generate 生成學生資料
        // 並收集為「可變 List」

        List<Student> students = Stream.generate(() -> Student.getRandomStudent(
                jmc,
                pymc))
                .limit(1000)
                .collect(Collectors.toList());

        // ======================================
        // Case 1: 空 List → Optional.empty
        // ======================================

        Optional<Student> o1 = getStudent(new ArrayList<>(), "first");

        System.out.println(
                "Empty = " + o1.isEmpty()
                        + ", Present = " + o1.isPresent());

        System.out.println(o1);

        // ⚠️ o1.get() 風險：
        //
        // 若 Optional 為空：
        // -> NoSuchElementException

        o1.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("---> Empty"));

        // ======================================
        // Case 2: 有資料的 Optional
        // ======================================

        Optional<Student> o2 = getStudent(students, "first");

        System.out.println(
                "Empty = " + o2.isEmpty()
                        + ", Present = " + o2.isPresent());

        System.out.println(o2);

        // 安全 consume
        o2.ifPresent(System.out::println);

        // ======================================
        // orElse vs orElseGet
        // ======================================

        // ⚠️ orElse（Eager）
        //
        // 無論 Optional 是否為空
        // 都會執行 getDummyStudent()

        Student firstStudent = o2.orElseGet(() -> getDummyStudent(jmc));

        long id = firstStudent.getStudentId();

        System.out.println(
                "firstStudent's id is " + id);

        // ======================================
        // Optional + Stream-like 操作
        // ======================================

        List<String> countries = students.stream()
                .map(Student::getCountryCode)
                .distinct()
                .toList();

        Optional.of(countries)

                .map(l -> String.join(",", l))

                .filter(l -> l.contains("FR"))

                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Missing FR"));
    }

    // ==========================================
    // Optional 方法設計規則
    // ==========================================
    //
    // ❌ 不可 return null
    //
    // ✔ 必須：
    // - Optional.empty()
    // - Optional.of()
    // - Optional.ofNullable()

    private static Optional<Student> getStudent(
            List<Student> list,
            String type) {

        if (list == null || list.isEmpty()) {

            return Optional.empty();

        } else if (type.equals("first")) {

            return Optional.ofNullable(list.get(0));

        } else if (type.equals("last")) {

            return Optional.ofNullable(
                    list.get(list.size() - 1));
        }

        return Optional.ofNullable(
                list.get(
                        new Random()
                                .nextInt(list.size())));
    }

    private static Student getDummyStudent(
            Course... courses) {

        System.out.println("Getting the dummy student");

        return new Student(
                "NO",
                1,
                1,
                "U",
                false,
                courses);
    }
}