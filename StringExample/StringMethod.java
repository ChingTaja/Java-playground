package StringExample;

public class StringMethod {
    public static void main(String[] args) {
        printInformation("Hello World");
        printInformation("");
        printInformation(" \t  \n");

        String helloWorld = "Hello World";
        System.out.printf("index of r = %d %n", helloWorld.indexOf('r'));

        System.out.printf("index of World = %d %n", helloWorld.indexOf("World"));

        System.out.printf("index of l = %d %n", helloWorld.indexOf('l'));

        System.out.printf("index of l = %d %n", helloWorld.lastIndexOf('l'));

        System.out.printf("index of l = %d %n", helloWorld.indexOf('l', 3));

        System.out.printf("index of l = %d %n", helloWorld.lastIndexOf('l', 8));

        String helloWorldLower = helloWorld.toLowerCase();
    
        if (helloWorld.equals(helloWorldLower)) {
            System.out.println("Value match exactly");
        }

        if (helloWorld.equalsIgnoreCase(helloWorldLower)) {
            System.out.println("Value match ignoring case");
        }

        if (helloWorld.startsWith("hello")) {
            System.out.println("String start with Hello");
        }


        if (helloWorld.endsWith("hello")) {
            System.out.println("String ends with Hello");
        }

        // contentEquals 方法不僅限於比較字串物件，它還可以用來比較 StringBuilder 的值
        //而 equals 方法則不支援這個功能

        if (helloWorld.contains("hello")) {
            System.out.println("String contains with Hello");
        }

        if (helloWorld.contentEquals("Hello Word")) {
            System.out.println("Values match exactly");
        }
    }
    
    

    public static void printInformation(String string) {
        int length = string.length();
    
        System.out.printf("Length = %d%n", length);
    
        if (string.isEmpty()) {
            System.out.println("String is Empty");
            return;
        }
    
        if (string.isBlank()) {
            System.out.println("String is Blank");
        }
    
        System.out.printf("First char = %c%n", string.charAt(0));
        System.out.printf("Last char = %c%n", string.charAt(length - 1));
    }
    
}
