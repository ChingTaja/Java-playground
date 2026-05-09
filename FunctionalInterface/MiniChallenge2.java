package FunctionalInterface;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class MiniChallenge2 {
    public static void main(String[] args) {

        // 這個 method 在 functional world 是什麼？ (String) → (String)
        // 有兩個選擇 
        // ✔ 1️⃣ Function<String, String> 
        // T → R
        Function<String, String> everySecondChar = source -> {
            StringBuilder returnVal = new StringBuilder();
            for (int i = 0; i < source.length(); i++) {
                if (i % 2 == 1) {
                    returnVal.append(source.charAt(i));
                }
            }
            return returnVal.toString();
        };

        // 2️⃣ UnaryOperator<String>
        // 👉 特化版本（同型別）
        // T → T
        UnaryOperator<String> everySecondCharOperator =
        source -> {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < source.length(); i++) {
                if (i % 2 == 1) {
                    result.append(source.charAt(i));
                }
            }
            return result.toString();
                };
        
            System.out.println(  everySecondChar.apply("1234567890"));
    
    }

    public static String everySecondChar(String source) {
        StringBuilder returnVal = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            if (i % 2 == 1) {
                returnVal.append(source.charAt(i));
            }
        }
        return returnVal.toString();
    }

    // 把 function（行為）當成參數傳來傳去
    // Functional Programming 的核心概念

    // 知道怎麼寫「能接收 lambda 的 method」
    // 不是使用：

    // forEach removeIf
    // replaceAll
    // 這種現成 target
   // 而是：自己寫一個 能接收 lambda的 method」
    public static String everySecondCharacter(Function<String, String> func, String source) {

        return func.apply(source);
    }
    
    /*
     * creating a method that's a target for a lambda
     * 
     * 這裡的 target：lambda 最後要對應到的 Functional Interface 位置
     * 
     * 舉例：
     * list.forEach(s -> System.out.println(s));
     * list.forEach(s -> System.out.println(s));
     * lambda 是 s -> System.out.println(s)
     * forEach(...) 接收他
     * 方法定義 void forEach(Consumer<? super T> action)
     * forEach = lambda 的 target , 它提供了一個：Consumer<T> 讓 lambda 放進去
     */
}
