package Inheritance;

public class StringMethod2 {

    public static void main(String[] args) {

        String birthDate = "04/04/1983";

        // 找年份起始位置
        int startingIndex = birthDate.indexOf("1983");
        System.out.println("startingIndex = " + startingIndex);

        // 取得年份
        System.out.println("Birth year = " + birthDate.substring(startingIndex));

        // 取得月份
        System.out.println("Month = " + birthDate.substring(3, 5));

        // String.join
        String newDate = String.join("/", "25", "11", "1983");
        System.out.println("newDate = " + newDate);

        // concat 一步一步組字串
        newDate = "25";
        newDate = newDate.concat("/");
        newDate = newDate.concat("11");
        newDate = newDate.concat("/");
        newDate = newDate.concat("1982");
        System.out.println("newDate = " + newDate);

        // concat 串接寫法
        newDate = newDate.concat("/").concat("11").concat("/").concat("1982");
        System.out.println("newDate = " + newDate);

        // replace
        System.out.println(newDate.replace('/', '-'));
        System.out.println(newDate.replace("2", "00"));

        // replaceFirst / replaceAll
        System.out.println(newDate.replaceFirst("/", "-"));
        System.out.println(newDate.replaceAll("/", "---"));

        // repeat
        System.out.println("ABC\n".repeat(3));
        System.out.println("-".repeat(3));

        // indent 正縮排
        System.out.println("ABC\n".repeat(3).indent(8));
        System.out.println("-".repeat(3));

        // indent 負縮排（去空白）
        System.out.println("   ABC\n".repeat(3).indent(-2));
        System.out.println("-".repeat(3));
    }
}
