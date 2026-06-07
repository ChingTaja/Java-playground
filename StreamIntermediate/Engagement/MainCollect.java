package StreamIntermediate.Engagement;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainCollect {

    public static void main(String[] args) {

        Course pymc = new Course("PYMC", "Python Masterclass");

        Course jmc = new Course("JMC", "Java Masterclass");

        // ------------------------------------
        // 深入實作高階 Reduction Operation
        //
        // 1. collect() 三參數多載版本
        // 2. Set 集合數學運算
        // 3. TreeSet 排序收集
        // 4. reduce() 字串聚合
        // ------------------------------------

        // 透過 Stream.generate
        // 生成 1000 名隨機學生
        //
        // 並使用 toList()
        // 匯入不可變 List

        List<Student> students =

                Stream.generate(() -> Student.getRandomStudent(
                        jmc,
                        pymc))

                        .limit(1000)

                        .toList();

        // =====================================
        // Collectors.toSet()
        // =====================================
        //
        // 預設底層：
        //
        // -> HashSet
        //
        // HashSet：
        //
        // 1. 不保證順序
        // 2. 依賴 hashCode()
        // 3. 依賴 equals()

        Set<Student> australianStudents = students.stream()

                .filter(s -> s.getCountryCode()
                        .equals("AU"))

                .collect(Collectors.toSet());

        System.out.println(
                "# of Australian Students = "
                        + australianStudents.size());

        // =====================================
        // 方案一：
        // Set Math（集合數學）
        // =====================================
        //
        // 當：
        //
        // - 主資料量巨大
        // - 子集合很小
        //
        // 使用：
        //
        // retainAll()
        //
        // 可能比重新跑 Stream
        // 更有效率

        Set<Student> underThirty = students.stream()

                .filter(s -> s.getAgeEnrolled() < 30)

                .collect(Collectors.toSet());

        System.out.println(
                "# of Under Thirty Students = "
                        + underThirty.size());

        // =====================================
        // 集合交集（Intersection）
        // =====================================

        Set<Student> youngAussies1 = new TreeSet<>(
                Comparator.comparing(
                        Student::getStudentId));

        // Union

        youngAussies1.addAll(australianStudents);

        // Intersection

        youngAussies1.retainAll(underThirty);

        youngAussies1.forEach(s -> System.out.print(
                s.getStudentId() + " "));

        System.out.println();

        // =====================================
        // 方案二：
        // collect() 三參數版本
        // =====================================

        // ⚠️ 踩坑：
        //
        // 若：
        //
        // .sorted()
        // .collect(Collectors.toSet())
        //
        // IntelliJ 會灰掉 sorted()
        //
        // 因為：
        //
        // HashSet 根本不保證順序
        //
        // 所以前面排序是浪費效能

        // ✅ 解法：
        //
        // 自訂 TreeSet
        //
        // 使用：
        //
        // collect(
        // supplier,
        // accumulator,
        // combiner
        // )

        Set<Student> youngAussies2 = students.stream()

                .filter(s -> s.getAgeEnrolled() < 30)

                .filter(s -> s.getCountryCode()
                        .equals("AU"))

                .collect(

                        // supplier
                        //
                        // 建立容器

                        () -> new TreeSet<>(
                                Comparator.comparing(
                                        Student::getStudentId)),

                        // accumulator
                        //
                        // 元素加入容器

                        TreeSet::add,

                        // combiner
                        //
                        // 平行 Stream 時
                        // 合併容器

                        TreeSet::addAll);

        youngAussies2.forEach(s -> System.out.print(
                s.getStudentId() + " "));

        System.out.println();

        // =====================================
        // reduce()
        // =====================================
        //
        // reduce 與 collect 最大差異：
        //
        // collect：
        // -> 收集進容器
        //
        // reduce：
        // -> 最終縮減成單一值

        String countryList =

                students.stream()

                        .map(Student::getCountryCode)

                        .distinct()

                        .sorted()

                        // reduce(identity, BinaryOperator)
                        //
                        // identity：
                        // 初始值
                        //
                        // r：
                        // 目前累加結果
                        //
                        // v：
                        // 新元素

                        .reduce(
                                "",
                                (r, v) -> r + " " + v);

        System.out.println(
                "countryList = " + countryList);
    }
}
