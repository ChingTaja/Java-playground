package MethodReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/*
 * =========================================
 * 🧠 PlainOld：用來觀察「物件建立時機」
 * =========================================
 */
class PlainOld {

    private static int last_id = 1;
    private int id;

    public PlainOld() {
        // 👉 每次 new 都會自動 +1
        id = PlainOld.last_id++;

        // 👉 用來觀察：什麼時候真的被建立
        System.out.println("Creating a PlainOld Object: id = " + id);
    }
}

/*
 * =========================================
 * 🧠 Main：Lambda / Method Reference / 延遲執行核心示範
 * =========================================
 */
public class Main {

    public static void main(String[] args) {

        /*
         * =========================================
         * 1️⃣ forEach + method reference
         * =========================================
         */

        List<String> list = new ArrayList<>(List.of(
                "Anna", "Bob", "Chuck", "Dave"));

        // 👉 method reference
        // 等同：s -> System.out.println(s)
        list.forEach(System.out::println);

        /*
         * =========================================
         * 2️⃣ BinaryOperator + method reference
         * =========================================
         */

        // Integer::sum
        // 等同： (a, b) -> a + b
        calculator(Integer::sum, 10, 25);

        // Double::sum
        calculator(Double::sum, 2.5, 7.5);

        /*
         * =========================================
         * 3️⃣ Constructor reference（重點）
         * =========================================
         */

        // 👉 Supplier<T> = 不吃參數，只回傳 T
        // PlainOld::new = constructor reference
        // 等價：() -> new PlainOld()

        Supplier<PlainOld> reference1 = PlainOld::new;

        /*
         * ⚠️ 注意：
         * 這一行「還沒有建立物件」
         * 只是把「怎麼建立物件」存起來
         */

        // 👉 這裡才真正執行 new PlainOld()
        PlainOld newPojo = reference1.get();

        /*
         * =========================================
         * 4️⃣ 批次產生物件（核心應用）
         * =========================================
         */

        System.out.println("Getting array");

        // 👉 用 Supplier + setAll 批次建立物件
        PlainOld[] pojo1 = seedArray(PlainOld::new, 10);

        // 手動寫 function
        calculator((s1, s2) -> s1.concat(s2), "Hello ", "World");
        // Java 幫你轉成 (s1, s2) -> s1.concat(s2)
        calculator(String::concat, "Hello ", "World");

        // 兩個相同型別 → 一個相同型別 BinaryOperator<T>
        BinaryOperator<String> b1 = String::concat; // unbounded
        // (T, U) → R
        BiFunction<String, String, String> b2 = String::concat;
        // 一個「輸入 T → 回傳 T」的函式
        UnaryOperator<String> u1 = String::toUpperCase;

        System.out.println(b1.apply("Hello ", "World"));
        System.out.println(b2.apply("Hello ", "World"));
        System.out.println(u1.apply("Hello "));

        String result = "Hello".transform(u1);
        System.out.println("Result = " + result);

        result = result.transform(String::toLowerCase);
        System.out.println("Result = " + result);

        Function<String, Boolean> f0 = String::isEmpty;
        boolean resultBoolean = result.transform(f0);
        System.out.println("Result = " + resultBoolean);
    }

    /*
     * =========================================
     * 5️⃣ Generic calculator（BinaryOperator）
     * =========================================
     *
     * 🧠 核心概念：
     * BinaryOperator<T> = (T, T) -> T
     *
     * 👉 把「運算行為」當參數傳入
     */
    private static <T> void calculator(BinaryOperator<T> function, T value1, T value2) {

        // 👉 真正執行 lambda / method reference
        T result = function.apply(value1, value2);

        System.out.println("Result of operation: " + result);
    }

    /*
     * =========================================
     * 6️⃣ seedArray（重點：延遲產生物件）
     * =========================================
     *
     * 🧠 Supplier<T>
     * = 不需要參數，但可以「產生 T」
     *
     * 👉 這裡的 reference 可能是：
     * PlainOld::new
     */
    private static PlainOld[] seedArray(Supplier<PlainOld> reference, int count) {

        // 👉 建立 array，但還沒放物件
        PlainOld[] array = new PlainOld[count];

        /*
         * =========================================
         * Arrays.setAll 核心概念
         * =========================================
         *
         * 👉 i = index
         * 👉 每一格都透過 lambda 產生值
         *
         * 等價概念：
         * for (int i = 0; i < count; i++) {
         * array[i] = reference.get();
         * }
         */

        Arrays.setAll(array, i -> reference.get());

        /*
         * 🧠 重點：
         * 每次 reference.get()
         * 才會真的 new PlainOld()
         */

        return array;
    }
}