package StreamIntermediate.Engagement;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Course pymc = new Course("PYMC", "Python Masterclass");
        Course jmc = new Course("JMC", "Java Masterclass");

        // 第一階段的單一物件測試程式碼（註解留存）
        // --------------------------------------------------

        // Student tim = new Student("AU", 2019, 30, "M",
        // true, jmc, pymc);
        //
        // System.out.println(tim);
        //
        // tim.watchLecture("JMC", 10, 5, 2019);
        // 模擬 Tim 在 2019 年 5 月觀看第 10 堂 Java
        //
        // tim.watchLecture("PYMC", 7, 7, 2020);
        // 模擬 Tim 在 2020 年 7 月觀看第 7 堂 Python
        //
        // System.out.println(tim);
        // 再次印出，確認 Map 內的數據已成功追蹤最新看課進度

        // --------------------------------------------------

        // 🛠️ 運用 Stream.generate 進行海量測試資料隨機生成
        //
        // 說明：
        // Stream.generate 接收一個無引數、有傳回值的 Supplier 介面
        //
        // 雖然 Lambda 參數區為空，
        // 但可以在函數本體內向靜態工廠方法傳入外部的課程變數
        //
        // （這兩個變數在環境中必須是 Effectively final）

        // Stream.generate(() -> Student.getRandomStudent(jmc, pymc))
        // .limit(10)
        // .forEach(System.out::println);

        Student[] students = new Student[1000];

        Arrays.setAll(
                students,
                (i) -> Student.getRandomStudent(jmc, pymc));

        // ------------------------------------------
        // 踩坑與異常觀念展示
        //
        // （此處以對照組說明講稿中提到的
        // Stream Reference 失效問題）
        // ------------------------------------------

        // ❌ 錯誤示範：

        // Stream<Student> maleStudents =
        // Arrays.stream(students);
        //
        // maleStudents.filter(
        // s -> s.getGender().equals("M"));
        //
        // 呼叫中間操作卻沒有將新 Stream 重新賦值
        //
        // System.out.println(maleStudents.count());
        //
        // ❌ 執行期會拋出 Exception！
        // 因為原參考已被宣告失效（Invalidated）

        // ✅ 正確示範：
        // 必須直接鏈結（Chain）
        // 或重新指派回傳的全新 Stream 參考

        var maleStudents = Arrays.stream(students)
                .filter(s -> s.getGender().equals("M"));

        System.out.println(
                "# of male students " + maleStudents.count());

        // 挑戰題 1：
        // 統計各性別的學生人數（M、F、U）
        //
        // 使用 List.of 建立一個可疊代的集合，
        // 透過 Loop 迴圈動態建立各性別的 Stream 管道

        for (String gender : List.of("M", "F", "U")) {

            var myStudents = Arrays.stream(students)
                    .filter(s -> s.getGender().equals(gender));

            System.out.println(
                    "# of " + gender + " students "
                            + myStudents.count());
        }

        // 挑戰題 2：
        // 統計三個年齡區間的人數
        //
        // （小於 30 歲、
        // 30 到 60 歲之間、
        // 大於等於 60 歲）
        //
        // 建立一個儲存 Predicate<Student>
        // 函數式介面的 List，
        // 將 Lambda 表達式當作變數封裝

        List<Predicate<Student>> list = List.of(

                (s) -> s.getAge() < 30,

                (Student s) -> s.getAge() >= 30
                        && s.getAge() < 60);

        // 優化考量：
        //
        // 刻意只對前兩個年齡區間
        // 進行 Stream Pipeline 走訪與計數
        //
        // 最後一個區間（>= 60）
        // 直接用總長度去減，
        // 避免進行第三次不必要的全部數據掃描
        //
        // 這在海量數據處理時對效能至關重要

        long total = 0;

        for (int i = 0; i < list.size(); i++) {

            /*
             * list.get(0)
             * 代表：
             *
             * s -> s.getAge() < 30
             *
             * （小於 30 歲的條件）
             *
             * Arrays.stream(students)
             * .filter(s -> s.getAge() < 30);
             *
             * ----------------------------------
             *
             * list.get(1)
             * 代表：
             *
             * s -> s.getAge() >= 30
             * && s.getAge() < 60
             *
             * （30 到 60 歲的條件）
             *
             * 倒出 1000 個學生，
             * 並丟入對應的濾網
             */

            var myStudents = Arrays.stream(students)
                    .filter(list.get(i));

            long cnt = myStudents.count();

            total += cnt;

            System.out.printf(
                    "# of students (%s) = %d%n",
                    i == 0
                            ? " < 30"
                            : ">= 30 & < 60",
                    cnt);
        }

        System.out.println(
                "# of students >= 60 = "
                        + (students.length - total));

        // 運用 summaryStatistics
        //
        // summaryStatistics()
        // 只能運作在：
        //
        // - IntStream
        // - DoubleStream
        // - LongStream
        //
        // 這裡透過 mapToInt
        // 將原本的泛型物件串流
        // 轉型為基本型別專屬串流
        //
        // 傳入方法參考 Student::getAgeEnrolled

        var ageStream = Arrays.stream(students)
                .mapToInt(Student::getAgeEnrolled);

        System.out.println(
                "Stats for Enrollment Age = "
                        + ageStream.summaryStatistics());

        // 複製並比對「當前年齡」統計

        var currentAgeStream = Arrays.stream(students)
                .mapToInt(Student::getAge);

        System.out.println(
                "Stats for Current Age = "
                        + currentAgeStream.summaryStatistics());

        // 運用無狀態中間操作
        // 進行資料清洗（去重與排序）

        Arrays.stream(students)

                // 1. 將 Student 物件串流
                // 映射為 String（國家代碼）串流

                .map(Student::getCountryCode)

                // 2. distinct()
                // 去除重複國家代碼
                //
                // 背後依賴：
                // equals() + hashCode()

                .distinct()

                // 3. sorted()
                // 進行自然排序（A ~ Z）

                .sorted()

                // 4. 終端操作

                .forEach(s -> System.out.print(s + " "));

        System.out.println();

        // 運用短路終端操作（Short-circuiting）
        //
        // anyMatch()
        // 只要遇到第一個符合條件的元素，
        // 整條 Stream 立刻停止

        boolean longTerm = Arrays.stream(students)

                .anyMatch(s -> (s.getAge() - s.getAgeEnrolled() >= 7)
                        &&
                        (s.getMonthsSinceActive() < 12));

        System.out.println(
                "longTerm students? " + longTerm);

        // 延伸：
        //
        // anyMatch()
        // 只會告訴你：
        //
        // true / false
        //
        // 若想知道精確數量，
        // 則應改用：
        //
        // filter() + count()

        long longTermCount = Arrays.stream(students)

                .filter(s -> (s.getAge() - s.getAgeEnrolled() >= 7)
                        &&
                        (s.getMonthsSinceActive() < 12))

                .count();

        System.out.println(
                "longTerm students? " + longTermCount);

        // -------------------------------------
        // 深入探討：
        //
        // 1. Stream.toList()
        // 2. Collectors.toList()
        // 3. toArray()
        // 4. Mutable Reduction
        // 5. Reduction Operations
        // -------------------------------------

        // Filter Chaining 與數量限制
        //
        // 挑選：
        //
        // 1. 老學員
        // 2. 無程式背景
        // 3. 限制 5 位
        //
        // 準備發送優惠券

        var longTimeLearners = Arrays.stream(students)

                // 第一層過濾：
                // 老學員條件

                .filter(s -> (s.getAge() - s.getAgeEnrolled() >= 7)
                        &&
                        (s.getMonthsSinceActive() < 12))

                // 第二層過濾：
                // 無程式背景者

                .filter(s -> !s.hasProgrammingExperience())

                // limit()
                // 短路中間操作
                //
                // 只要取得 5 筆，
                // 後面資料不再處理

                .limit(5)

                // toArray()
                //
                // 若不傳入參數：
                //
                // -> Object[]
                //
                // 傳入 Student[]::new：
                //
                // -> Student[]
                //
                // 對應 IntFunction：
                //
                // size -> new Student[size]

                .toArray(Student[]::new);

        // collect(Collectors.toList())
        //
        // 屬於：
        //
        // Mutable Reduction Operation
        //
        // 回傳：
        //
        // 可變（Modifiable）的 List

        var learners = Arrays.stream(students)

                .filter(s -> (s.getAge() - s.getAgeEnrolled() >= 7)
                        &&
                        (s.getMonthsSinceActive() < 12))

                .filter(s -> !s.hasProgrammingExperience())

                .limit(5)

                .collect(Collectors.toList());

        // 證明可變性：
        //
        // 因為 learners 是 Mutable List，
        // 所以可以成功 shuffle()

        Collections.shuffle(learners);

        // ⚠️ 若改用：
        //
        // .toList()
        //
        // 則會回傳：
        //
        // Unmodifiable List
        //
        // shuffle()
        // 將直接拋出：
        //
        // UnsupportedOperationException
    }
}