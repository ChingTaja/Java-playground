package FunctionalInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>(List.of(
                "alpha", "bravo", "charlie", "delta"));

        // =========================
        // 傳統 for-each 遍歷
        // =========================
        for (String s : list) {
            System.out.println(s);
        }

        System.out.println("-------");

        // =========================
        // Lambda + forEach
        // Consumer<T>（接受元素，不回傳）
        // =========================
        list.forEach((var myString) -> System.out.println(myString));

        System.out.println("-------");

        // =========================
        // Lambda closure（使用外部變數 prefix）
        // =========================
        String prefix = "nato";
        list.forEach((var myString) -> {
            char first = myString.charAt(0);
            System.out.println(prefix + " " + myString + " means " + first);
        });

        // =========================
        // Generic Method + BinaryOperator
        // =========================
        // BinaryOperator<T> = (T, T) -> T
        // 用來做「二元運算（+ - * / 拼接等）」
        int result = calculator((var a, var b) -> a + b, 5, 2);

        var result2 = calculator((a, b) -> a / b, 10.0, 2.5);

        var result3 = calculator(
                (a, b) -> a.toUpperCase() + " " + b.toUpperCase(),
                "Ralph", "Kramden");

        // =========================
        // List of coordinates (double[])
        // =========================
        var coords = Arrays.asList(
                new double[] { 47.2160, -95.2348 },
                new double[] { 29.1566, -89.2495 },
                new double[] { 35.1556, -90.0659 });

        coords.forEach(s -> System.out.println(Arrays.toString(s)));

        // =========================
        // BiConsumer<T, U>
        // =========================
        // BiConsumer = 接收兩個參數，不回傳
        BiConsumer<Double, Double> p1 = (lat, lng) -> System.out.printf("[lat:%.3f lon:%.3f]%n", lat, lng);

        var firstPoint = coords.get(0);
        processPoint(firstPoint[0], firstPoint[1], p1);

        System.out.println("-------");

        coords.forEach(s -> processPoint(s[0], s[1], p1));

        coords.forEach(s -> processPoint(s[0], s[1],
                (lat, lng) -> System.out.printf("[lat:%.3f lon:%.3f]%n", lat, lng)));

        // =========================
        // removeIf + Predicate
        // =========================

        /*
         * removeIf:
         * 用來「條件式移除 List 元素」
         * removeIf 會用你給的「判斷函式」，去檢查每個元素，符合條件就刪掉
         *
         * Predicate<T>:
         * - 接收元素
         * - 回傳 boolean
         * - true → 移除該元素
         * - false → 保留
         */

        list.removeIf(s -> s.equalsIgnoreCase("bravo"));
        list.forEach(s -> System.out.println(s));

        System.out.println("-------");

        /*
         * Predicate 的應用：條件刪除
         * s.startsWith("ea") → true → 移除
         */
        list.removeIf(s -> s.startsWith("ea"));

        list.forEach(s -> System.out.println(s));

        list.addAll(List.of("echo", "easy", "earnest"));
        list.forEach(s -> System.out.println(s));

        System.out.println("-------");
        list.removeIf(s -> s.startsWith("ea"));
        list.forEach(s -> System.out.println(s));

        // =========================
        // replaceAll + UnaryOperator
        // =========================
        /*
         * List.replaceAll：
         * 👉 使用 UnaryOperator<T>
         * 👉 對每個元素做「轉換」
         * 👉 輸入 = 輸出（同型別）
         *
         * 核心概念：
         * Function 類型中的「同型別轉換」
         * 適合：更新 / 修改元素內容
         */
        list.replaceAll(s -> s.charAt(0) + " - " + s.toUpperCase());
        System.out.println("-------");
        list.forEach(s -> System.out.println(s));

        String[] emptyStrings = new String[10];

        /*
         * ⚠️ 重要概念：
         * new String[10] 預設值是 null
         * 不是 ""（empty string）
         *
         * Arrays.fill：
         * 👉 把所有元素填成指定值
         */

        System.out.println(Arrays.toString(emptyStrings));
        Arrays.fill(emptyStrings, "");
        System.out.println(Arrays.toString(emptyStrings));

        // =========================
        // Arrays.setAll + IntFunction
        // =========================
        /*
         * Arrays.setAll：
         * 👉 使用 IntFunction<T>
         * 👉 以 index 作為輸入
         * 👉 產生每個位置的值
         *
         * 核心概念：
         * Function 類型的「index-based transformation」
         */

        Arrays.setAll(emptyStrings, (i) -> "" + (i + 1) + ". ");
        System.out.println(Arrays.toString(emptyStrings));

        // =========================
        // Lambda + switch expression
        // =========================
        /*
         * Lambda 特性：
         * 👉 可以內嵌 switch expression
         * 👉 支援複雜邏輯但仍保持單行寫法
         */

        Arrays.setAll(emptyStrings, (i) -> "" + (i + 1) + ". "
                + switch (i) {
                    case 0 -> "one";
                    case 1 -> "two";
                    case 2 -> "three";
                    default -> "";
                });
        System.out.println(Arrays.toString(emptyStrings));

        // =========================
        // Supplier + random generation
        // =========================
        String[] names = { "Ann", "Bob", "Carol", "David", "Ed", "Fred" };

        /*
         * Supplier<T>：
         * 👉 不接收參數
         * 👉 只「提供」值
         *
         * 這裡用來產生 random index
         */
        // 呼叫方式：「決定行為」
        String[] randomList = randomlySelectedValues(15, names,
                () -> new Random().nextInt(0, names.length));
        System.out.println(Arrays.toString(randomList));

    }

    // =========================
    // Generic Method + BinaryOperator
    // =========================
    /*
     * BinaryOperator<T>
     * 👉 (T, T) -> T
     * 👉 用於「兩個相同型別輸入 → 一個輸出」
     *
     * T operate(T value1, T value2)
     */
    public static <T> T calculator(BinaryOperator<T> function, T value1, T value2) {

        T result = function.apply(value1, value2);
        System.out.println("Result of operation: " + result);
        return result;
    }

    /*
     * 將「行為（lambda）」當參數傳入方法
     *
     * BiConsumer<T, T>
     * 👉 (T, T) -> void
     * 👉 用於「消費兩個值（不回傳）」
     */
    public static <T> void processPoint(T t1, T t2, BiConsumer<T, T> consumer) {
        consumer.accept(t1, t2);
    }

    // =========================
    // Function 類別應用（資料生成 / 轉換）
    // =========================
    /*
     * Function 類型應用：
     * 👉 本質是「資料轉換 / 生成」
     *
     * IntFunction / Supplier 混合應用：
     * 👉 以 index 或外部邏輯產生資料
     */
    // 定義規則 (我要一個方法，但我需要三個東西)
    // 我要幾個結果(count)
    // 從哪裡選(values)
    // 怎麼決定 index(supplier)
    public static String[] randomlySelectedValues(int count,
            String[] values,
            Supplier<Integer> s) {
        // 方法邏輯：真正做事
        // 用 supplier 提供的 index，把 values 組成結果
        //supplier 提供「變動部分」
        // values 是資料來源

        String[] selectedValues = new String[count];
        for (int i = 0; i < count; i++) {
            selectedValues[i] = values[s.get()];
        }
        return selectedValues;
    }

}