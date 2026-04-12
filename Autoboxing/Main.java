package Autoboxing;

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

    }

    private static Double getDoubleObject() {

        return Double.valueOf(100.00);
    }

    private static double getLiteralDoublePrimitive() {

        return 100.0;
    }
}
