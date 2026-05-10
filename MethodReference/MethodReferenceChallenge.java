package MethodReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;

/*
用 Function / UnaryOperator + lambda + method reference + String.transform()
對「字串陣列做一連串可組合的轉換」
*/
public class MethodReferenceChallenge {
    private static Random random = new Random();

    private record Person(String first) {
        public String last(String s) {
            return first + " " + s.substring(0, s.indexOf(" "));
        }
    }

    public static void main(String[] args) {
        String[] names = { "Ann", "Bob", "Carol", "David", "Ed", "Fred" };

        Person tim = new Person("Tim");

        // method reference - String::toUpperCase
        // 等同於 s -> s.toUpperCase()

        /*
         * method reference 能用的條件
         * 
         * ✔ 只能是：
         * 
         * s -> s.someMethod()
         * 
         * 或：
         * 
         * ClassName::methodName
         */
        List<UnaryOperator<String>> list = new ArrayList<>(List.of(String::toUpperCase,
                s -> s += " " + getRandomChar('D', 'M') + ".",
                s -> s += " " + reverse(s, 0, s.indexOf(" ")),
                // 去這個 class 裡找 reverse 這個 static method
                MethodReferenceChallenge::reverse,
                String::new, // s -> new String(s)
                s -> new String("Howdy" + s),
                String::valueOf, // x -> String.valueOf(x)
                tim::last, // s -> tim.last(s) , 用 tim 這個物件，去呼叫它的 last 方法
                (new Person("MARY"))::last));

        applyChanges(names, list);
    }

    // List<UnaryOperator<String>> stringFunctions 裝 “方法” 的 list

    /*
     * 核心概念：
     * 這個 method 的目的，是把「一堆 String → String 的轉換規則（lambda / method reference）」
     * 依序套用到 names array 上
     *
     * 本質：Function pipeline
     */
    private static void applyChanges(String[] names,
            List<UnaryOperator<String>> stringFunctions) {
        /*
         * Step 1：
         * Arrays.asList(names)
         *
         * 👉 把 Array 包裝成 List
         * ⚠️ 注意：這個 List 是「backed by array」
         *
         * 意思是：
         * 👉 改 List = 同時改 Array
         */

        List<String> backedByArray = Arrays.asList(names);

        /*
         * 🧠 Step 2：
         * 外層迴圈：逐一拿出「轉換 function」
         *
         * stringFunctions 裡面裝的是：
         * 👉 UnaryOperator<String>（String → String 的轉換）
         */
        for (var function : stringFunctions) {
            /*
             * 🧠 Step 3：
             * replaceAll = 對 List 每個元素做「更新」
             *
             * 👉 本質：
             * for each element:
             * s = function(s)
             */

            // function 會對「每個元素」執行一次
            backedByArray.replaceAll(s ->
            /*
             * 🧠 Step 4：
             * 
             * transform 是哪裡來的？
             * 👉 它是 String 類別的方法
             * 把自己交給一個 function 處理，然後回傳新結果
             * 等同：
             * function.apply(s)
             */
            s.transform(function)); // 把 s 丟進 function再拿回結果
            System.out.println(Arrays.toString(names));
        }

        /*
         * replaceAll 例子
         * 
         * List<String> list = new ArrayList<>(List.of("anna", "bob", "charlie"));
         * 
         * list.replaceAll(s -> s.toUpperCase());
         * 
         * System.out.println(list);
         * 
         */
    }

    private static char getRandomChar(char startChar, char endChar) {
        return (char) random.nextInt((int) startChar, (int) endChar + 1);
    }

    private static String reverse(String s) {
        return reverse(s, 0, s.length());
    }

    private static String reverse(String s, int start, int end) {
        return new StringBuilder(s.substring(start, end)).reverse().toString();
    }
}