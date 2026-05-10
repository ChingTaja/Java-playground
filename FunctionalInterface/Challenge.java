package FunctionalInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// 目的：
// 1. 把 Lambda 傳給 Arrays / List 的方法
// method(target for lambda)
// 2. 提供 transformation function , 讓 API 幫我處理所有元素
// 3. String → String transformation
// 4. 修改 List 時，原本的 array 也要同步被修改
// 5. 理解 Functional API 的用途
/*
| API        | 用途           |
| ---------- | ------------ |
| replaceAll | 批次更新元素       |
| setAll     | 用 index 產生元素 |
| removeIf   | 條件式移除        |
| forEach    | 批次執行行為       |
*/

public class Challenge {
    private static Random random = new Random();

    public static void main(String[] args) {
        String[] names = { "Anna", "Taja", "Tom" };

        // 修改 List 時，原本的 array 也要同步被修改
        // ❌ List<String> list = new ArrayList<>(Arrays.asList(names)); -> 原本 array 不會變

        Arrays.setAll(names, i -> names[i].toUpperCase()); // 方法1 IntFunction

        List<String> backedByArray = Arrays.asList(names);
        backedByArray.replaceAll(s -> s.toUpperCase());// 方法2 UnaryOperator

        System.out.println("Transform to Uppercase");
        backedByArray.replaceAll(s -> s + " " + getRandomChar('A', 'D'));

        System.out.println(Arrays.toString(names));

        backedByArray.replaceAll(s -> s += " " + getReverseName(s.split(" ")[0]));

        System.out.println("Add reversed name as last name");
        backedByArray.forEach(s -> System.out.println(s));

        List<String> newList = new ArrayList<>(List.of(names));
        // 找第一個單字
        /*
         * newList.removeIf(s -> s.substring(0, s.indexOf(" "))
         * // 找最後一個單字
         * .equals(s.substring(s.lastIndexOf(" ") + 1)));
         */

        // return statement
        newList.removeIf(s -> {

            String firstName = s.substring(0, s.indexOf(" "));
            // 找最後一個單字
            String lastName = s.substring(s.lastIndexOf(" ") + 1);

            return firstName.equals(lastName);
        });

        System.out.println("Remove names where first = last");
        newList.forEach(s -> System.out.println(s));
    }

    private static char getRandomChar(char startChar, char endChar) {
        return (char) random.nextInt((int) startChar, (int) endChar + 1);
    }

    private static String getReverseName(String firstName) {
        return new StringBuilder(firstName).reverse().toString();
    }
}
