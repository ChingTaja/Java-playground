package StreamIntermediate.Engagement;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class MainChallenge {

    public static void main(String[] args) {

        Course pymc = new Course("PYMC", "Python Masterclass", 50);

        Course jmc = new Course("JMC", "Java Masterclass", 100);

        Course jgames = new Course("JGAME", "Creating games in Java");

        // ---------------------------------------------
        // Stream 管道實戰：
        //
        // 1. IntStream.rangeClosed() 取代 iterate
        // 2. reduce() 手動累加
        // 3. sorted + filter 組合優化
        // 4. TreeSet Comparator uniqueness 問題
        // 5. 省略 collect 提升效能
        // ---------------------------------------------

        // =========================================
        // 生成 5000 名隨機學生
        // =========================================
        //
        // ⚠️ 為避免 Stream.iterate 的副作用與錯誤遞增問題
        //
        // 使用：
        //
        // IntStream.rangeClosed(1, 5000)

        List<Student> students = IntStream.rangeClosed(1, 5000)
                .mapToObj(i -> Student.getRandomStudent(
                        jmc,
                        pymc))
                .toList();

        // =========================================
        // reduce 手動計算完成率總和
        // =========================================
        //
        // identity = 0
        //
        // Double::sum = (a, b) -> a + b

        double totalPercent = students.stream()
                .mapToDouble(s -> s.getPercentComplete("JMC"))
                .reduce(0, Double::sum);

        double avePercent = totalPercent / students.size();

        System.out.printf(
                "Average Percentage Complete = %.2f%% %n",
                avePercent);

        int topPercent = (int) (1.25 * avePercent);

        System.out.printf(
                "Best Percentage Complete = %d%% %n",
                topPercent);

        // =========================================
        // 排序規則（入學年份）
        // =========================================

        Comparator<Student> longTermStudent = Comparator.comparing(
                Student::getYearEnrolled);

        // =========================================
        // 方案一：收集成 List
        // =========================================

        List<Student> hardWorkers = students.stream()

                .filter(s -> s.getMonthsSinceActive("JMC") == 0)

                .filter(s -> s.getPercentComplete("JMC") >= topPercent)

                .sorted(longTermStudent)

                .limit(10)

                .toList();

        // ⚠️ 注意：
        //
        // List 不可變（unmodifiable）
        //
        // 但裡面的 Student 仍可 mutate

        hardWorkers.forEach(s -> {

            s.addCourse(jgames);

            System.out.print(s.getStudentId() + " ");
        });

        System.out.println();

        // =========================================
        // ⚠️ TreeSet uniqueness bug
        // =========================================
        //
        // 問題：
        //
        // Comparator.compare == 0
        // => TreeSet 視為同一元素
        //
        // 導致資料遺失

        Comparator<Student> uniqueSorted = longTermStudent.thenComparing(
                Student::getStudentId);

        // =========================================
        // 方案二：直接 stream + forEach
        // （最佳效能版本）
        // =========================================

        students.stream()

                .filter(s -> s.getMonthsSinceActive("JMC") == 0)

                .filter(s -> s.getPercentComplete("JMC") >= topPercent)

                .sorted(longTermStudent)

                .limit(10)

                // 不 collect、不存集合
                //
                // 直接處理資料流

                .forEach(s -> {

                    s.addCourse(jgames);

                    System.out.print(
                            s.getStudentId() + " ");
                });
    }
}