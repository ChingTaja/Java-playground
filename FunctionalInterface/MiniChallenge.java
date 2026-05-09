package FunctionalInterface;

import java.util.Arrays;
import java.util.function.Consumer;

public class MiniChallenge {
    public static void main(String[] args) {
        Consumer<String> printWords = new Consumer<String>() {

            @Override
            public void accept(String sentence) {
                String[] parts = sentence.split((" "));
                for (String part : parts) {
                    System.out.println(part);
                }
            }
        };

        // 把原本的匿名 class / for-loop / method call 改寫成 lambda」
        // 1. 找方法（只有一個 abstract method）
        // 2. 找 Functional Interface , 找參數 (void accept(T t))
        // 3. 找 return / 行為
        Consumer<String> printWordsLambda = sentence -> {
            String[] parts = sentence.split((" "));
            for (String part : parts) {
                System.out.println(part);
            }
        };

        // execute
        printWords.accept("Let's split this up into an array");
        printWordsLambda.accept("Let's split this up into an array");

        Consumer<String> printWordsForEach = sentence -> {
            String[] parts = sentence.split((" "));
            // for (String part : parts) {
            //     System.out.println(part);
            // }
            Arrays.asList(parts).forEach(s -> System.out.println(s));
        };

        // 把「行為」交給了 Consumer後 , 用 accept 執行
        printWordsForEach.accept("Let's split this up into an array");


        Consumer<String> printWordsConcise = sentence -> {
            Arrays.asList(sentence.split((" "))).forEach(s -> System.out.println(s));
        };

        printWordsConcise.accept("Let's split this up into an array");
    }
}
