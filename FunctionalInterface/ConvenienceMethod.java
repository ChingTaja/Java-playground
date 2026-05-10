package FunctionalInterface;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ConvenienceMethod {
    public static void main(String[] args) {
        String name = "Tim";
        Function<String, String> uCase = String::toUpperCase;
        System.out.println(uCase.apply(name));

        Function<String, String> lastName = s -> s.concat("abc");

        /*
         * andThen()
         *
         * 意思:
         * 「我做完後，再給下一個 function 做」
         *
         * 流程:
         *
         * Tim
         * ↓ uCase
         * TIM
         * ↓ lastName
         * TIMabc
         *
         * 所以:
         * 最後結果 = TIMabc
         */
        Function<String, String> uCaseLastName = uCase.andThen(lastName);

        /*
         * compose()
         *
         * 跟 andThen 相反
         *
         * compose 的意思:
         * 「你先做，我再做」
         *
         * 流程:
         *
         * Tim
         * ↓ lastName
         * Timabc
         * ↓ uCase
         * TIMABC
         *
         * 所以:
         * 最後結果 = TIMABC
         */
        uCaseLastName = uCase.compose(lastName);

        Function<String, String[]> f0 = uCase
                .andThen(s -> s.concat("abc"))
                .andThen(s -> s.split(" "));
        System.out.println(Arrays.toString(f0.apply(name)));

        Function<String, String> f1 = uCase
                .andThen(s -> s.concat("abc"))
                .andThen(s -> s.split(" "))
                .andThen(s -> s[1].toUpperCase() + ", " + s[0]);

        System.out.println(Arrays.toString(f0.apply(name)));
        System.out.println(f1.apply(name));

        Function<String, Integer> f2 = uCase
                .andThen(s -> s.concat("abc"))
                .andThen(s -> s.split(" "))
                .andThen(s -> String.join(",", s))
                .andThen(String::length);

        System.out.println(f2.apply(name));

        // Consumer: 同一份資料給不同 Consumer 使用
        // chaining 不會改變資料
        String[] names = { "Ann", "Bov", "Taja" };
        Consumer<String> s0 = s -> System.out.println(s.charAt(0));
        Consumer<String> s1 = System.out::println;
        Arrays.asList(names).forEach(s0
                .andThen(s -> System.out.print(" - "))
                .andThen(s1));

        // 條件組合
        Predicate<String> p1 = s -> s.equals("Tim");
        Predicate<String> p2 = s -> s.equalsIgnoreCase("Tim");
        Predicate<String> p3 = s -> s.startsWith("T");
        Predicate<String> p4 = s -> s.endsWith("e");

        Predicate<String> combined1 = p1.or(p2);
        System.out.println("Combined 1=" + combined1.test(name));

        Predicate<String> combine2 = p3.and(p4);
        System.out.println("Combined 2=" + combine2.test(name));

        Predicate<String> combine3 = p3.and(p4).negate();
        System.out.println("Combined 3=" + combine3.test(name));

        record Person(String firstName, String lastName) {
        }

        List<Person> list = new ArrayList<>(Arrays.asList(
                new Person("Peter", "Pan"),
                new Person("Peter", "PumpkinEater"),
                new Person("Minnie", "Mouse"),
                new Person("Mickey", "Mouse")));

        // | 1. 傳統 lambda Comparator 寫法
        // sort 需要一個 Comparator
        // Comparator 本質： (o1, o2) -> int
        // compareTo 回傳：
        // < 0  → o1 排前面
        // = 0  → 相同
        // > 0  → o2 排前面
        list.sort((o1, o2) -> o1.lastName.compareTo(o2.lastName));
        list.forEach(System.out::println);

        // 2. Comparator.comparing()
        // Comparator 提供的 static convenience method
        // comparing 會接收： Function<T, U> , 並自動產生 Comparator<T>
        // Person::lastName 等於 p -> p.lastName()
        // 請幫我用 lastName 排序
        System.out.println("------------------------------------");
        list.sort(Comparator.comparing(Person::lastName));
        list.forEach(System.out::println);

        System.out.println("------------------------------------");
        list.sort(Comparator.comparing(Person::lastName)
        // thenComparing 是： 先比 lastName 如果相同 再比 firstName (multi-level sorting)

                .thenComparing(Person::firstName));
        list.forEach(System.out::println);

        System.out.println("------------------------------------");
        list.sort(Comparator.comparing(Person::lastName)
                .thenComparing(Person::firstName).reversed());
        list.forEach(System.out::println);
    }
}
