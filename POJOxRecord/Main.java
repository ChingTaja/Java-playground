package POJOxRecord;
// contrived example
public class Main {
    public static void main(String[] args) {
        // 建立五個學生物件
        for (int i = 1; i <= 5; i++) {
            String id = "S92300" + i;
            String name;
            switch (i) {
                case 1 -> name = "Mary";
                case 2 -> name = "Carol";
                case 3 -> name = "Tim";
                case 4 -> name = "Harry";
                case 5 -> name = "Lisa";
                default -> name = "Anonymous";
            }
            Student s = new Student(id, name, "1985-11-05", "Java Masterclass");
            System.out.println(s);
            // 每個物件傳給 println 時，如果該類別有實作 toString() 方法，Java 會自動執行它
        }
    }
}