package NestedClass;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import NestedClass.domain.StoreEmployee;
import NestedClass.domain.Employee;
import NestedClass.domain.EmployeeComparator;

public class RunMethods {

    public static void main(String[] args) {

        List<StoreEmployee> storeEmployees = new ArrayList<>(List.of(
                new StoreEmployee(10015, "Meg", 2019,
                        "Target"),
                new StoreEmployee(10515, "Joe", 2021,
                        "Walmart"),
                new StoreEmployee(10105, "Tom", 2020,
                        "Macys"),
                new StoreEmployee(10215, "Marty", 2018,
                        "Walmart"),
                new StoreEmployee(10322, "Bud", 2016,
                        "Target")));

        var c0 = new EmployeeComparator<StoreEmployee>();
        // static nested class
        var c1 = new Employee.EmployeeComparator<StoreEmployee>();
        // inner class
        var c2 = new StoreEmployee().new StoreComparator<StoreEmployee>();

        // Using local class
        class NameSort<T> implements Comparator<StoreEmployee> {

            @Override
            public int compare(StoreEmployee o1, StoreEmployee o2) {
                return o1.getName().compareTo(o2.getName());
            }
        }

        var c3 = new NameSort<StoreEmployee>();

        // Using anonymous class
        // 直接 new 一個 class
        // Comparator 不是你創的 class

        /*
         * 
         * new 後面寫的
         * 
         * interface -> 我「實作」它
         * class -> 我「繼承」它
         */
        
        /*
         * // Java 偷偷幫你做 (Comparator 是 interface)
         * class Anonymous implements Comparator {
         * ...
         * }
         * 
         * // 你其實在做這個
         * new Anonymous();
         */
        var c4 = new Comparator<StoreEmployee>() {
            @Override
            public int compare(StoreEmployee o1, StoreEmployee o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };

        sortIt(storeEmployees, c0);
        sortIt(storeEmployees, c1);
        sortIt(storeEmployees, c2);
        sortIt(storeEmployees, c3);
        sortIt(storeEmployees, c4);
        /*
         * 匿名 class 直接當參數
         * 
         */
        
        /*
         * sortIt(storeEmployees, new Comparator<StoreEmployee>() {
         * 
         * @Override
         * public int compare(StoreEmployee o1, StoreEmployee o2) {
         * return o1.getName().compareTo(o2.getName());
         * }
         * });
         * 
         */

        // lambda
        sortIt(storeEmployees, (o1, o2) -> o1.getName().compareTo(o2.getName()));
    }

    public static <T> void sortIt(List<T> list,
            Comparator<? super T> comparator) {

        System.out.println("Sorting with Comparator: " + comparator.toString());
        list.sort(comparator);
        for (var employee : list) {
            System.out.println(employee);
        }
    }

}

// 編譯後 JVM 會用 $ 表示 nested class
