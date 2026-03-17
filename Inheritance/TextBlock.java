package Inheritance;

public class TextBlock {

    /*
    只是一種
    多行字串multi-line String literals 的特殊格式
    它其實就是字串
    只是在原始程式碼中的表示方式不同
    自 JDK 15 起
    它已成為官方語言的一部分
    */
    public static void main(String[] args) {
        String bulletIt = "Print a Bulleted Lost:\n" + "\u2022 First Point\n";

        System.out.println(bulletIt);

        String textBlock = """
		 Print a Bulleted List:
                \u2022 First Point""";
            
        System.out.println(textBlock);

        int age = 35;
        System.out.printf("Your age is %d%n", age);

        int yearOfBirth = 2025 - age;
        System.out.printf("Age = %d , Birth year = %d" , age , yearOfBirth);

        System.out.printf("Your age is %.2f%n", (float) age);

        for(int i = 1 ; i < 10000 ; i *=10) {
            System.out.printf("Printing %6d %n", i);
        }
        
        String formattedString = String.format("Your age is %d", age);
        System.out.println(formattedString);

        formattedString = "Your age is %d".formatted(age);

        System.out.println(formattedString);
    }
}
