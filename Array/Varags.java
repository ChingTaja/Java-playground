package Array;

public class Varags {
    public static void main(String... args) {
        String[] splitStrings = "Hello World".split(" ");
        printText(splitStrings);

        printText("Hello", "World", "again"); // 參數為 String[] textList 時不允許傳入
        
        printText(); // 參數為 String[] textList 時不允許傳入

        printText(splitStrings); // 參數為 String[] textList 時允許傳入


    }
    
    private static void printText(String... textList) {
        for (String t : textList) {
            System.out.println(t);
        }
    }
}
