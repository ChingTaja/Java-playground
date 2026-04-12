package Autoboxing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //  人工 boxed 不建議
        Integer boxedInt = Integer.valueOf(15);

        // deprecated since JDK 9
        Integer deprecatedBoxing = new Integer(15);
        // unnecessary
        int unboxedInt = boxedInt.intValue();

        // Automatic
        Integer autoBoxed = 15;
        int autoUnboxed = autoBoxed;
        System.out.println(autoBoxed.getClass().getName());

        // autoUnboxed 是原始型別沒有 getClass 方法可以呼叫
        // System.out.println(autoUnboxed.getClass().getName());

        Double resultBoxed = getLiteralDoublePrimitive();
        double resultUnboxed = getDoubleObject();

        Integer[] wrapperArray = new Integer[5];
        wrapperArray[0] = 50;
        System.out.println(Arrays.toString(wrapperArray));

        System.out.println(wrapperArray[0].getClass().getName());

        Character[] characterArray = { 'a', 'b', 'c', 'd' };
        System.out.println(Arrays.toString(characterArray));

        // var ourList = getList(1, 2, 3, 4, 5);

        var ourList = List.of(1, 2, 3, 4, 5); // (不可變列表)
        System.out.println(ourList); // Java 會在背後自動將這些 int 轉換成 Integer 物件

    }

    // 一個方法不能有兩個 ... 參數 (可變參數)
    // 告訴編譯器：這個方法可以接受 0 個、1 個或多個 Integer 物件
    // 當你想傳入不特定數量的參數時
    private static ArrayList<Integer> getList(Integer... varargs) {

        ArrayList<Integer> aList = new ArrayList<>();
        for (int i : varargs) {
            aList.add(i);
        }
        return aList;
    }
    
    private static int returnAnInt(Integer i) {

        return i;
    }

    private static Integer returnAnInteger(int i) {

        return i;
    }

    private static Double getDoubleObject() {

        return Double.valueOf(100.00);
    }

    private static double getLiteralDoublePrimitive() {

        return 100.0;
    }
}
