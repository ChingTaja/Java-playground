package FunctionalInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;

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
         * removeIf：
         * 用來「條件式移除 List 元素」
         * removeIf 會用你給的「判斷函式」，去檢查每個元素，符合條件就刪掉
         *
         * Predicate<T>：
         * - 接收元素
         * - 回傳 boolean
         * - true → 移除該元素
         * - false → 保留
         */

        list.removeIf(s -> s.equalsIgnoreCase("bravo"));
        list.forEach(s -> System.out.println(s));

        list.addAll(List.of("echo", "easy", "earnest"));
        list.forEach(s -> System.out.println(s));

        System.out.println("-------");

        /*
         * Predicate 的應用：條件刪除
         * s.startsWith("ea") → true → 移除
         */
        list.removeIf(s -> s.startsWith("ea"));

        list.forEach(s -> System.out.println(s));
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
}